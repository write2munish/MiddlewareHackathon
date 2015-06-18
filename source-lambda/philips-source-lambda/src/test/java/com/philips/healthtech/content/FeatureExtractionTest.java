package com.philips.healthtech.content;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.List;

/**
 * TODO: Description of class
 *
 * @author mlavaert
 */
public class FeatureExtractionTest {


    @Test
    public void testFetchFeatures() throws Exception {

        List<String> stuff =  JsonPath.read(INPUT, "$..ProductCrossReference");

        System.out.println(stuff);



    }

    public static final String INPUT =
            "{\n" +
            "  \"content\": {\n" +
            "    \"Product\": {\n" +
            "      \"ID\": \"PRD-HC781342\",\n" +
            "      \"Name\": {\n" +
            "        \"content\": \"Ingenia 4.0T MR system\",\n" +
            "        \"QualifierID\": \"en\"\n" +
            "      },\n" +
            "      \"ProductCrossReference\": [\n" +
            "        {\n" +
            "          \"ProductID\": \"FEA-100222\",\n" +
            "          \"QualifierID\": \"AllCountries\",\n" +
            "          \"Type\": \"FEA\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"ProductID\": \"FEA-100221\",\n" +
            "          \"QualifierID\": \"AllCountries\",\n" +
            "          \"Type\": \"FEA\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"UserTypeID\": \"PRD\",\n" +
            "      \"Values\": {\n" +
            "        \"ValueGroup\": [\n" +
            "          {\n" +
            "            \"AttributeID\": \"PH-Code-CTN\",\n" +
            "            \"Value\": {\n" +
            "              \"content\": \"HC781342\",\n" +
            "              \"Derived\": true,\n" +
            "              \"DerivedContextID\": \"AA_en\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"AttributeID\": \"PH-MT-Descriptor\",\n" +
            "            \"Value\": {\n" +
            "              \"content\": \"MR system\",\n" +
            "              \"QualifierID\": \"en\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"AttributeID\": \"PH-MT-Concept\",\n" +
            "            \"Value\": {\n" +
            "              \"content\": \"Ingenia\",\n" +
            "              \"QualifierID\": \"en\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"AttributeID\": \"PH-MT-MarketingText\",\n" +
            "            \"Value\": {\n" +
            "              \"content\": \"At the forefront of clinical excellence -- Diagnostic confidence, explore advanced applications, and generate the productivity required to meet today’s healthcare challenges with the Ingenia 3.0T. Through dStream, Ingenia delivers premium image quality with digital clarity and speed –and with iPatient, it provides patient-centric imaging, from patient set-up to image result.\",\n" +
            "              \"QualifierID\": \"en\"\n" +
            "            }\n" +
            "          },\n" +
            "          {\n" +
            "            \"AttributeID\": \"TypicalHomogeneityAt45CMDSV\",\n" +
            "            \"Value\": {\n" +
            "              \"content\": \"≤ 1.1\",\n" +
            "              \"QualifierID\": \"en\",\n" +
            "              \"UnitID\": \"unece.unit.ppm\"\n" +
            "            }\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"id\": \"en_AA_PRD-HC781342\"\n" +
            "}";
}
