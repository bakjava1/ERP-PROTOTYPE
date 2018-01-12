package at.ac.tuwien.sepm.assignment.group02.server.util;

import com.github.javafaker.Faker;


public class CreateTestData {

    private Faker faker;

    CreateTestData(){
        faker = new Faker();
    }

    public static void main(String[] args){
        Faker faker = new Faker();
        System.out.println(faker.name().firstName());
        System.out.println(faker.name().lastName());
        System.out.println(faker.chuckNorris().fact());
        System.out.println(faker.beer().name());
        faker.number().numberBetween(1,2);
    }

}
