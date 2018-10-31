package com.team5.utilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpreadsheetHelpersTest {
  @Test
  @DisplayName("test convert uppercase letter ranking for 'A'")
  void testConvertUppercaseFirstLetter() {
    assertEquals(1, SpreadsheetHelpers.convertUppercaseLetterAlphabetRanking('A'));
  }

  @Test
  @DisplayName("test convert uppercase letter ranking for 'M'")
  void testConvertUppercaseMidLetter() {
    assertEquals(13, SpreadsheetHelpers.convertUppercaseLetterAlphabetRanking('M'));
  }

  @Test
  @DisplayName("test convert uppercase letter ranking for 'Z'")
  void testConvertUppercaseLastLetter() {
    assertEquals(26, SpreadsheetHelpers.convertUppercaseLetterAlphabetRanking('Z'));
  }

  @Test
  @DisplayName("test convert uppercase for invalid letter 'a'")
  void testConvertUppercaseInvalidLetter() {
    assertEquals(-1, SpreadsheetHelpers.convertUppercaseLetterAlphabetRanking('a'));
  }

  @Test
  @DisplayName("test convert uppercase for invalid character '$'")
  void testConvertUppercaseInvalidCharacter() {
    assertEquals(-1, SpreadsheetHelpers.convertUppercaseLetterAlphabetRanking('$'));
  }

  @Test
  @DisplayName("test convert lowercase letter ranking for 'a'")
  void testConvertLowercaseFirstLetter() {
    assertEquals(1, SpreadsheetHelpers.convertLowercaseLetterAlphabetRanking('a'));
  }

  @Test
  @DisplayName("test convert lowercase letter ranking for 'm'")
  void testConvertLowercaseMidLetter() {
    assertEquals(13, SpreadsheetHelpers.convertLowercaseLetterAlphabetRanking('m'));
  }

  @Test
  @DisplayName("test convert lowercase letter ranking for 'z'")
  void testConvertLowercaseLastLetter() {
    assertEquals(26, SpreadsheetHelpers.convertLowercaseLetterAlphabetRanking('z'));
  }

  @Test
  @DisplayName("test convert lowercase for invalid letter 'A'")
  void testConvertLowercaseInvalidLetter() {
    assertEquals(-1, SpreadsheetHelpers.convertLowercaseLetterAlphabetRanking('A'));
  }

  @Test
  @DisplayName("test convert lowercase for invalid character '$'")
  void testConvertLowercaseInvalidCharacter() {
    assertEquals(-1, SpreadsheetHelpers.convertLowercaseLetterAlphabetRanking('$'));
  }

  @Test
  @DisplayName("test cell coords convert with 'A1'")
  void testCellCoordsConvertSingleLetterAndNumber() {
    assertArrayEquals(new int[] {1, 1}, SpreadsheetHelpers.convertCellCoordsNumberedArray("A1"));
  }

  @Test
  @DisplayName("test cell coords convert with 'BM7'")
  void testCellCoordsConvertMultipleLetters() {
    assertArrayEquals(new int[] {7, 65}, SpreadsheetHelpers.convertCellCoordsNumberedArray("BM7"));
  }

  @Test
  @DisplayName("test cell coords convert with 'B709'")
  void testCellCoordsConvertMultipleNumbers() {
    assertArrayEquals(new int[] {709, 2}, SpreadsheetHelpers.convertCellCoordsNumberedArray("B709"));
  }
}
