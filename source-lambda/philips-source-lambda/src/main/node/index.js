// dependencies
var async = require('async');
var AWS = require('aws-sdk');
var util = require('util');
var dynamodb = new AWS.DynamoDB({region : 'eu-west-1'});

var s3 = new AWS.S3();
var kinesis = new AWS.Kinesis({region : 'eu-west-1'});

var kinesisStream = "delta-to-canonical";


var newItem;

exports.handler = function(event, context) {
    // Read options from the event.
    console.log("Reading options from event:\n", util.inspect(event, {depth: 5}));
    var srcBucket = event.Records[0].s3.bucket.name;
    // Object key may have spaces or unicode non-ASCII characters.
    var srcKey    =
        decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, " "));

    console.log("src-key", srcKey);

    // Download the image from S3, transform, and upload to a different S3 bucket.
    async.waterfall([
            function download(next) {
                console.log("bucket" + srcBucket);
                // Download the image from S3 into a buffer.
                s3.getObject({
                        Bucket: srcBucket,
                        Key: srcKey
                    },
                    next);
            },
            function getFileAndId(response, next){
                console.log("got file");
                var id = srcKey;
                var data = response.Body.toString();
                console.log("id" + id);

                next(null, id, data);
            },
            function putItem(id, data, next) {
                var flattenData = data.replace(/(\n|\n\r|\r|\s)/g, "");
                console.log("id in putItem", id);
                console.log("putItem, flattenData: " + flattenData);
                var params = {
                    Item: {
                        id: {
                            S: 'productId'
                        },
                        value: {
                            S: flattenData
                        }
                    },
                    TableName : 'source_product',
                    ReturnValues : 'ALL_OLD'
                };

                newItem = params.Item.value.S;

                dynamodb.putItem(params, function(err, data) {
                    if (err) console.log(err, err.stack); // an error occurred
                    else {

                        var changed = false;
                        if(!data.Attributes){
                            changed = true;
                            console.log("data is empty");
                        } else {
                            console.log("data: " + JSON.stringify(data));
                            console.log("existing " + data.Attributes.value.S);
                            var oldItem = data.Attributes.value.S.replace(/(\n|\n\r|\r|\s)/g, "");
                            console.log("flattened old " + oldItem);
                            if(oldItem != newItem){
                                console.log("Item changed");

                                changed = true;
                            } else {
                                console.log("Item unchanged");
                            }
                        }

                        if(changed){
                            writeToKinesis(kinesisStream, newItem.toString());
                        } else {
                            context.done();
                        }

                    }
                    next(null);
                });
            }
        ], function (err) {
            if (err) {
                console.error(
                    'Unable to put in DDB' + srcBucket + '/' + srcKey +
                    ' due to an error: ' + err
                );
            } else {
                console.log(
                    'Success ' + srcBucket + '/' + srcKey
                );
            }
        }
    );

    function writeToKinesis(streamName, data) {
        console.log("StreamName: " + streamName);
        console.log("Data: " + data);

        var randomNumber = Math.floor(Math.random() * 100000);
        var partitionKey = 'pk-' + randomNumber;
        var recordParams = {
            Data: data,
            PartitionKey: partitionKey,
            StreamName: streamName
        };

        console.log("Before Kinesis Put");

        kinesis.putRecord(recordParams, function(err, data) {
            if (err) {
                console.error("Kinesis Error: " + err);
            } else {
                console.log("Kinesis: " + JSON.stringify(data));
            }
            console.log("After Kinesis Put");
            context.done();
        });
    }
};

