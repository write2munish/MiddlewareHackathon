package com.philips.glueteam.stepsimulator;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mikke on 17-06-2015.
 */
public class StepSimulator {
    public static final List<String> CONTEXT = Arrays.asList("AA_en", "DE_de", "NL_nl", "DK_da");
    public static final String PREFIX = "hackaton_";
    public static final int NUMBER_OF_PRODUCTS = 250;
    public static final int NUMBER_OF_FEATURES = 1200;

    public static AWSCredentialsProvider CREDENTIALS = new AWSCredentialsProviderChain(new PropertiesFileCredentialsProvider("creds.propeties"));

}
