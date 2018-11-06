package com.team5.utilities;

/**
 * A helper class for spreadsheet related data-conversions.
 */
public final class SpreadsheetHelpers {
    /**
     * Converts a letter-number cell coordinate into an integer array tuple formatted [row number, column number].
     * 
     * @param cellCoordsStr The letter-number cell coordinate. Must be of form "XX...X00...0" where X is an arbitrary letter and 0 is an arbitrary number.
     * @return Returns an integer array tuple formatted [row number, column number].
     */
    public static int[] convertCellCoordsNumberedArray(String cellCoordsStr) {
        // Split the number and letter parts apart
        String number_part = "";
        String letter_part = "";
        for (char c : cellCoordsStr.toCharArray()) {
            if (Character.isDigit(c))
                number_part += c;
            if (Character.isLetter(c)) {
                letter_part += c;
            }
        }

        // Compute the cell number of the number part
        int number_part_val = Integer.parseInt(number_part);

        // Compute the cell number of the letter part
        int exp = 0;
        int letter_part_val = 0;
        for (int i = letter_part.length()-1; i >= 0; i--) {
            char c = letter_part.charAt(i);
            int letter_rank = convertUppercaseLetterAlphabetRanking(c);
            letter_part_val += letter_rank * (int)Math.pow(26, exp);
            exp++;
        }

        // Create and return the output array
        int[] ret = {number_part_val, letter_part_val}; // (Row, Column)
        return ret;
    }

    /**
     * Converts an uppercase letter into its alphabetical number placement. So A=1, B=2, ... , Z=26. Returns -1 if the character is not an uppercase character.
     * 
     * @param c The uppercase letter.
     * @return Returns the alphabetical number placement of the uppercase character or -1 if the character is invalid.
     */
    public static int convertUppercaseLetterAlphabetRanking(char c) {
        int val_c = (int)c;

        int subtractor_upper = 64; //for upper case
        if(val_c <= 90 & val_c >= 65)
            return val_c-subtractor_upper;

        return -1;
    }

    /**
     * Converts a lowercase letter into its alphabetical number placement. So a=1, b=2, ... , z=26. Returns -1 if the character is not a lowercase character.
     * 
     * @param c The lowercase letter.
     * @return Returns the alphabetical number placement of the lowercase character or -1 if the character is invalid.
     */
    public static int convertLowercaseLetterAlphabetRanking(char c) {
        int val_c = (int)c;

        int subtractor_lower = 96; //for lower case
        if(val_c <= 122 & val_c >=97)
            return val_c-subtractor_lower;

        return -1;
    }
}