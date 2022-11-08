//import java.util.*;

/**
 * A class that helps to use Rabin Karp algorithm.
 * The public methods are advised.
 * Call methods with strings themselves.
 * 
 * @author cary61
 */

class RabinKarp {

    /**
     * The char weight that put on any character.
     */
    static final long P = 131;

    /**
     * The mod of Rabin Karp algorithm.
     */
    static final long MOD = 1610612741;

    /**
     * The length of weight array.
     */
    static int maxStringLength = 0;

    /**
     * Store the weight of any range
     */
    static long[] weight = new long[maxStringLength + 1];

    /**
     * Store the rolling hash arrray of any string.
     */
    static java.util.Map<String, long[]> stringToHash = new java.util.HashMap<>();

    /**
     * Prepare the weight array, initial length of 200, before using.
     */
    static {
        weight[0] = 1;
    }

    /**
     * Find the index of the string needle in string haystack, if non-existed, return -1.
     * 
     * @param haystack the string that will be searched
     * @param needle the string that should be found
     * @return the index of needle in haystack, if non-existed, -1
     */
    public static int indexOf(String haystack, String needle) {
        return indexOf(haystack, 0, haystack.length() - 1, needle, 0, needle.length() - 1);
    }
    
    /**
     * Find the index of string needle in string haystack, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystack the string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needle the string that should be found
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(String haystack, int fromIndex, String needle) {
        return indexOf(haystack, fromIndex, haystack.length() - 1, needle, 0, needle.length() - 1);
    }

    /**
     * Find the index of the part of string needle in string haystack, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystack the string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needle the string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(String haystack, int fromIndex, String needle, int beginIndex, int endIndex) {
        return indexOf(haystack, fromIndex, haystack.length() - 1, needle, beginIndex, endIndex);
    }

    /**
     * Find the index of the part of string needle in part of string haystack, if non-existed, return -1.
     * 
     * @param haystack the string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needle the string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(String haystack, int fromIndex, int toIndex, String needle, int beginIndex, int endIndex) {
        int n =  endIndex - beginIndex + 1;
        if (toIndex - fromIndex + 1 < n) {
            return -1;
        }
        long[] haystackHash = stringToHash.computeIfAbsent(haystack, str -> getHash(str));
        long[] needleHash = stringToHash.computeIfAbsent(needle, str -> getHash(str));
        long weightValue = weight[n];
        long targetHash = ((needleHash[endIndex + 1] - needleHash[beginIndex] * weightValue) % MOD + MOD) % MOD;
        ++toIndex;
        for (int r = n + fromIndex; r <= toIndex; ++r) {
            if ( ((haystackHash[r] - haystackHash[r - n] * weightValue) % MOD + MOD) % MOD == targetHash ) {
                return r - n;
            }
        }
        return -1;
    }

    /**
     * Check if one string matches another.
     * 
     * @param str1 the first string
     * @param str2 the second string
     * @return if they are matched
     */
    public static boolean match(String str1, String str2) {
        return match(str1, 0, str1.length() - 1, str2, 0, str2.length() - 1);
    }

    /**
     * Check if str1[beginIndex : endIndex] matches str2.
     * 
     * @param str1 the first string
     * @param beginIndex the lower bound of index of the first string
     * @param endIndex the upper bound of index of the first string
     * @param str2 the second string
     * @return if they are matched
     */
    public static boolean match(String str1, int beginIndex, int endIndex, String str2) {
        return match(str1, beginIndex, endIndex, str2, 0, str2.length() - 1);
    }

    /**
     * Check if str1 matches str2[beginIndex : endIndex].
     * 
     * @param str1 the first string
     * @param str2 the second string
     * @param beginIndex the lower bound of index of the second string
     * @param endIndex the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(String str1, String str2, int beginIndex, int endIndex) {
        return match(str1, 0, str1.length() - 1, str2, beginIndex, endIndex);
    }
 
    /**
     * Check if str1[beginIndex1 : endIndex1] matches str2[beginIndex2 : endIndex2].
     * 
     * @param str1 the first string
     * @param beginIndex1 the lower bound of index of the first string
     * @param endIndex1 the upper bound of index of the first string
     * @param str2 the second string
     * @param beginIndex2 the lower bound of index of the second string
     * @param endIndex2 the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(String str1, int beginIndex1, int endIndex1, String str2, int beginIndex2, int endIndex2) {
        int n = endIndex1 - beginIndex1 + 1;
        if (n != endIndex2 - beginIndex2 + 1) {
            return false;
        }
        long[] hash1 = stringToHash.computeIfAbsent(str1, str -> getHash(str));
        long[] hash2 = stringToHash.computeIfAbsent(str2, str -> getHash(str));
        return (((hash1[endIndex1 + 1] - hash1[beginIndex1] * weight[n]) % MOD + MOD) % MOD) 
            == (((hash2[endIndex2 + 1] - hash2[beginIndex2] * weight[n]) % MOD + MOD) % MOD);
    }


    
    // Implementations



    /**
     * Get the hash array of a string, with the specified P and MOD.
     * @param str the string
     * @param P the char weight in Rabin Karp algorithm
     * @param MOD the mod in Rabin Karp algorithm
     * @return the hash array
     */
    static long[] getHash(String str) {
        int len = str.length();
        if (len > maxStringLength) {
            grow(len);
        }
        long[] hash = new long[len + 1];
        for (int i = 0; i < len; ++i) {
            hash[i + 1] = (hash[i] * P + str.charAt(i)) % MOD;
        }
        return hash;
    }
    
    /**
     * Extends the weight array.
     * 
     * @param newLength the new length of weight array
     */
    static void grow(int newLength) {
        long[] newWeight = new long[newLength + 1];
        for (int i = 0; i <= maxStringLength; ++i) {
            newWeight[i] = weight[i];
        }
        for (int i = maxStringLength; i < newLength; ++i) {
            newWeight[i + 1] = newWeight[i] * P % MOD;
        }
        weight = newWeight;
        maxStringLength = newLength;
    }
}
