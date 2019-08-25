package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Package;
import com.mobiquityinc.builder.PackageBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The Packer class.
 *
 */
class Packer {

    public static void main(String... args) throws APIException {

        if (args.length != 1)
            throw new APIException("Incorrect number of parameters.", null);

        System.out.println(Packer.pack(args[0]));
    }

    /**
     * Process input file to create a list of packages with items optimized by weight and price
     * @param fileName Full path and filename to process.
     * @return String of packages and items.
     * @throws APIException Errors on the input file.
     */
    public static String pack(String fileName) throws APIException {
        final List<String> lines;
        PackageBuilder builder = new PackageBuilder();

        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new APIException("Error Processing File.", e);
        }

        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            try {
                Package packageToSend = builder.buildPackage(line);
                result.append(packageToSend.indexesToString()).append("\n");
            } catch (APIException e) {
                throw new APIException("Error processing file content.", e);
            }
        }

        return result.toString();
    }
}
