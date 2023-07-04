package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.util.Resources;
import org.util.Xml;
import org.xmlunit.assertj.XmlAssert;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;


class UserTest {
    static User user;

    @BeforeAll
    static  void setup(){
//        Can be used to setup a database connection
        user = new User("Marco", 37, false, LocalDate.now().minusYears(37));
        System.out.println("Setup was called");
    }

    @AfterAll
    static void cleanUp(){
//        Has to be static in order to execute for all tests
        user = null;
        System.out.println("Cleanup was called");
    }

    @Test
//    Do not abuse the display name anottation
    @DisplayName("User name should be Marco")
    void userShouldNameMarco(){
//        JUNIT way to do it:
//        Assertions.assertTrue(user.name().startsWith("Marco") );
        assertThat(user.name()).startsWith("Marc");
    }
    @Test
    void userShouldBeAtLeast18(){
//        JUNIT way to do it:
//        Assertions.assertTrue(user.age() >= 18 );
        assertThat(user.age()).isGreaterThanOrEqualTo(18);
    }
    @Test
    void userIsNotBlocked(){
//        Adding a description
        assertThat(user.blocked()).
                as("User %s is not blocked", user.name()).
                isFalse();
    }
    @Test
    void userJSONShouldBeEqualToInstance (){
//        While testing json objects order does not matter
        assertThatJson(user).isEqualTo("{\"name\":\"Marco\",\"age\":37,\"blocked\":false,\"birthDate\":[1986, 7, 4]}");
    }
    @Test
    void userXMLIsCorrect(){
//        Cannot user static import as we did with JSON bc the AssertThat method of both classes will crash
        XmlAssert.assertThat("<a><b attr=\"abc\"></b></a>").nodesByXPath("//a/b/@attr").exist();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
//    the parameters of the method are the columns in the csv file
    void allUsersHaveToBeAtLeast18(String name, int age){
        assertThat(age).isGreaterThanOrEqualTo(18);
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestsCreatedThroughCode() {
        List<Xml> xmls = Resources.toStrings("users.*\\.xml");

        return xmls.stream().map(xml -> DynamicTest.dynamicTest(xml.name(), () -> {
            XmlAssert.assertThat(xml.content()).hasXPath("/users/user/name");
        })).collect(Collectors.toList());
    }
}