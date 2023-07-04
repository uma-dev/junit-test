package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    @Test
    void twoPlusTwoEqualsFour (){
        //SimpleCalculator calculator = new SimpleCalculator();
        //since Java 10 we can use LOCAL VARIABLE inference
        var calculator = new SimpleCalculator();
        assertEquals( 4, calculator.add(2,2) );
    }
}