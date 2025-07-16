package com.minsait.user_api.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileCleanerDemo {

    public static void main(String[] args) {
        String inputFile = "src/main/resources/input.txt";
        String outputFile = "src/main/resources/output.txt";

        try {
            var lines = Files.lines(Paths.get(inputFile))
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());

            Files.write(Paths.get(outputFile), lines);

            System.out.println("Arquivo processado com sucesso!");
            System.out.println("Linhas restantes: " + lines.size());

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
