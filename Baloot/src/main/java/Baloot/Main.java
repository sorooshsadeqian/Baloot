package Baloot;


import Baloot.Api.API;
import Baloot.Api.APIImpl;
import Baloot.CLI.CommandLine;
import Baloot.Database.Database;
import Baloot.Database.InMemoryDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//{“username”: “user1”, “password”: “1234”, “email”: “user@gmail.com”, “birthDate”: “1977-09-15”, “address”: “address1”, “credit”: 1500}
public class Main {
    public static void main(String[] args) {
        Database database = new InMemoryDatabase();
        API api = new APIImpl(database);
        CommandLine cli = new CommandLine(api);
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                String line = cin.readLine();
                String output = cli.runCommand(line);
                System.out.println(output);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}