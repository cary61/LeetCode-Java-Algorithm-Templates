//import java.util.*;

/**
 * A class that helps to use Rabin Karp algorithm.
 * The public methods are advised.
 * There are 3 solutions to use this class.
 * 1. Call methods with the strings themselves, this solution is suitable for large amounts of short strings.
 * 2. Get the hash of strings, then call methods with them, this solution is suitable for several long strings.
 * 3. Instantiate object for strings, then call methods with them, this solution compares 2 different hash arrays, is the safest.
 * 
 * @author cary61
 */

class RabinKarp {

    /**
     * The Rabin Karp String object, encapsulates 2 different hash arrays of one string.
     */
    static class RabinString {

        /**
         * The first hash array.
         */
        long[] hashA;

        /**
         * The second hash array.
         */
        long[] hashB;
    }

    /**
     * The char weight that put on any character.
     */
    static final long P1 = 131;
    static final long P2 = 193;

    /**
     * The mod of Rabin Karp algorithm.
     */
    static final long MOD1 = 1610612741;
    static final long MOD2 = 402653189;

    /**
     * The length of weight array.
     */
    static int maxStringLength = 0;

    /**
     * Store the weight of any range
     */
    static long[] weight1 = new long[maxStringLength + 1];
    static long[] weight2 = new long[maxStringLength + 1];

    /**
     * Store the rolling hash arrray of any string.
     */
    static java.util.Map<String, long[]> stringToHash = new java.util.HashMap<>();

    /**
     * Prepare the weight array, initial length of 200, before using.
     */
    static {
        weight1[0] = 1;
        weight2[0] = 1;
    }





    // Solution 1:





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
        long[] haystackHash = stringToHash.computeIfAbsent(haystack, str -> getHash(str));
        long[] needleHash = stringToHash.computeIfAbsent(needle, str -> getHash(str));
        return indexOf(haystackHash, fromIndex, toIndex, needleHash, beginIndex, endIndex, P1, MOD1);
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
        return (((hash1[endIndex1 + 1] - hash1[beginIndex1] * weight1[n]) % MOD1 + MOD1) % MOD1) 
            == (((hash2[endIndex2 + 1] - hash2[beginIndex2] * weight1[n]) % MOD1 + MOD1) % MOD1);
    }





    // Solution 2:





    /**
     * Get the hash array of a string.
     * 
     * @param str the string
     * @return the hash array
     */
    public static long[] getHash(String str) {
        return getHash(str, P1, MOD1);
    }

    /**
     * Find the index of the string needle in string haystack by their hash arrays, if non-existed, return -1.
     * 
     * @param haystackHash the hash array of string that will be searched
     * @param needleHash the hash array of string that should be found
     * @return the index of needle in haystack, if non-existed, -1
     */
    public static int indexOf(long[] haystackHash, long[] needleHash) {
        return indexOf(haystackHash, 0, haystackHash.length - 2, needleHash, 0, needleHash.length - 2);
    }
    
    /**
     * Find the index of string needle in string haystack by their hash arrays, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackHash the hash array of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleHash the hash array of string that should be found
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(long[] haystackHash, int fromIndex, long[] needleHash) {
        return indexOf(haystackHash, fromIndex, haystackHash.length - 2, needleHash, 0, needleHash.length - 2);
    }

    /**
     * Find the index of the part of string needle in string haystack by their hash arrays, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackHash the hash array of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleHash the hash array of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(long[] haystackHash, int fromIndex, long[] needleHash, int beginIndex, int endIndex) {
        return indexOf(haystackHash, fromIndex, haystackHash.length - 2, needleHash, beginIndex, endIndex);
    }

    /**
     * Find the index of the part of string needle in part of string haystack by their hash arrays, if non-existed, return -1.
     * 
     * @param haystackHash the hash array of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needleHash the hash array of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(long[] haystackHash, int fromIndex, int toIndex, long[] needleHash, int beginIndex, int endIndex) {
        return indexOf(haystackHash, fromIndex, toIndex, needleHash, beginIndex, endIndex, P1, MOD1);
    }

    /**
     * Check if 2 strings are matched by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param hash2 the hash array of the second string
     * @return if they are matched
     */
    public static boolean match(long[] hash1, long[] hash2) {
        return match(hash1, 0, hash1.length - 2, hash2, 0, hash2.length - 2, P1, MOD1);
    }

    /**
     * Check if str1[beginIndex : endIndex] matches str2 by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param beginIndex the lower bound of index of the first string
     * @param endIndex the upper bound of index of the first string
     * @param hash2 the hash array of the second string
     * @return if they are matched
     */
    public static boolean match(long[] hash1, int beginIndex, int endIndex, long[] hash2) {
        return match(hash1, beginIndex, endIndex, hash2, 0, hash2.length - 2, P1, MOD1);
    }

    /**
     * Check if a string and a part of string are matched by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param hash2 the hash array of the second string
     * @param beginIndex the lower bound of index of the second string
     * @param endIndex the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(long[] hash1, long[] hash2, int beginIndex, int endIndex) {
        return match(hash1, 0, hash1.length - 2, hash2, beginIndex, endIndex, P1, MOD1);
    }

    /**
     * Check if 2 parts of strings are matched by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param beginIndex1 the lower bound of index of the first string
     * @param endIndex1 the upper bound of index of the first string
     * @param hash2 the hash array of the second string
     * @param beginIndex2 the lower bound of index of the second string
     * @param endIndex2 the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(long[] hash1, int beginIndex1, int endIndex1, long[] hash2, int beginIndex2, int endIndex2) {
        return match(hash1, beginIndex1, endIndex1, hash2, beginIndex2, endIndex2, P1, MOD1);
    }





    // Solution 3:

    



    /**
     * Construct the RabinString instance.
     * @param str the string that will be converted to RabinString
     * @return the RabinString refers to original string
     */
    public static RabinString of(String str) {
        RabinString rabin = new RabinString();
        rabin.hashA = getHash(str, P1, MOD1);
        rabin.hashB = getHash(str, P2, MOD2);
        return rabin;
    }

    /**
     * Find the index of the string needle in string haystack by their RabinStrings, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinString of string that will be searched
     * @param needleRabin the RabinString of string that should be found
     * @return the index of needle in haystack, if non-existed, -1
     */
    public static int indexOf(RabinString haystackRabin, RabinString needleRabin) {
        return indexOf(haystackRabin, 0, haystackRabin.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }
    
    /**
     * Find the index of string needle in string haystack by their RabinStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleRabin the RabinString of string that should be found
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinString haystackRabin, int fromIndex, RabinString needleRabin) {
        return indexOf(haystackRabin, fromIndex, haystackRabin.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }

    /**
     * Find the index of the part of string needle in string haystack by their RabinStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleRabin the RabinString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinString haystackRabin, int fromIndex, RabinString needleRabin, int beginIndex, int endIndex) {
        return indexOf(haystackRabin, fromIndex, haystackRabin.hashA.length - 2, needleRabin, beginIndex, endIndex);
    }

    /**
     * Find the index of the part of string needle in part of string haystack by their RabinStrings, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needleRabin the RabinString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinString haystackRabin, int fromIndex, int toIndex, RabinString needleRabin, int beginIndex, int endIndex) {
        int idx1 = indexOf(haystackRabin.hashA, fromIndex, toIndex, needleRabin.hashA, beginIndex, endIndex, P1, MOD1);
        if (idx1 == -1) return -1;
        int idx2 = indexOf(haystackRabin.hashB, fromIndex, toIndex, needleRabin.hashB, beginIndex, endIndex, P2, MOD2);
        return idx1 == idx2 ? idx1 : -1;
    }

    /**
     * Check if one string matches another by their RabinStrings.
     * 
     * @param str1 the RabinString of first string
     * @param str2 the RabinString of second string
     * @return if they are matched
     */
    public static boolean match(RabinString str1, RabinString str2) {
        return match(str1, 0, str1.hashA.length - 2, str2, 0, str2.hashA.length - 2);
    }

    /**
     * Check if str1[beginIndex : endIndex] matches str2 by their RabinStrings.
     * 
     * @param str1 the RabinString of first string
     * @param beginIndex the lower bound of index of the first string
     * @param endIndex the upper bound of index of the first string
     * @param str2 the RabinString of second string
     * @return if they are matched
     */
    public static boolean match(RabinString str1, int beginIndex, int endIndex, RabinString str2) {
        return match(str1, beginIndex, endIndex, str2, 0, str2.hashA.length - 2);
    }

    /**
     * Check if str1 matches str2[beginIndex : endIndex] by their RabinStrings.
     * 
     * @param str1 the RabinString of first string
     * @param str2 the RabinString of second string
     * @param beginIndex the lower bound of index of the second string
     * @param endIndex the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(RabinString str1, RabinString str2, int beginIndex, int endIndex) {
        return match(str1, 0, str1.hashA.length - 2, str2, beginIndex, endIndex);
    }
 
    /**
     * Check if str1[beginIndex1 : endIndex1] matches str2[beginIndex2 : endIndex2] by their RabinStrings.
     * 
     * @param str1 the RabinString of first string
     * @param beginIndex1 the lower bound of index of the first string
     * @param endIndex1 the upper bound of index of the first string
     * @param str2 the RabinString of second string
     * @param beginIndex2 the lower bound of index of the second string
     * @param endIndex2 the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(RabinString str1, int beginIndex1, int endIndex1, RabinString str2, int beginIndex2, int endIndex2) {
        return match(str1.hashA, beginIndex1, endIndex1, str2.hashA, beginIndex2, endIndex2, P1, MOD1)
            && match(str1.hashB, beginIndex1, endIndex1, str2.hashB, beginIndex2, endIndex2, P2, MOD2);
    }





    // Implementations



    /**
     * Find the index of the part of string needle in part of string haystack by their hash arrays, if non-existed, return -1.
     * 
     * @param haystackHash the hash array of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needleHash the hash array of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @param P the P in Rabin Karp algorithm
     * @param MOD the MOD in Rabin Karp algorigthm
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    static int indexOf(long[] hash1, int fromIndex, int toIndex, long[] hash2, int beginIndex, int endIndex, long P, long MOD) {
        int n =  endIndex - beginIndex + 1;
        if (toIndex - fromIndex + 1 < n) {
            return -1;
        }
        long weightValue = P == P1 ? weight1[n] : weight2[n];
        long targetHash = ((hash2[endIndex + 1] - hash2[beginIndex] * weightValue) % MOD + MOD) % MOD;
        ++toIndex;
        for (int r = n + fromIndex; r <= toIndex; ++r) {
            if ( ((hash1[r] - hash1[r - n] * weightValue) % MOD + MOD) % MOD == targetHash ) {
                return r - n;
            }
        }
        return -1;
    }

    /**
     * Check if 2 parts of strings are matched by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param beginIndex1 the lower bound of index of the first string
     * @param endIndex1 the upper bound of index of the first string
     * @param hash2 the hash array of the second string
     * @param beginIndex2 the lower bound of index of the second string
     * @param endIndex2 the upper bound of index of the second string
     * @param P the P in Rabin Karp algorithm
     * @param MOD the MOD in Rabin Karp algorigthm
     * @return if they are matched
     */
    static boolean match(long[] hash1, int beginIndex1, int endIndex1, long[] hash2, int beginIndex2, int endIndex2, long P, long MOD) {
        int n = endIndex1 - beginIndex1 + 1;
        if (n != endIndex2 - beginIndex2 + 1) {
            return false;
        }
        long[] weight;
        if (P == P1)    weight = weight1;
        else            weight = weight2;
        return (((hash1[endIndex1 + 1] - hash1[beginIndex1] * weight[n]) % MOD + MOD) % MOD) 
            == (((hash2[endIndex2 + 1] - hash2[beginIndex2] * weight[n]) % MOD + MOD) % MOD);
    }

    /**
     * Get the hash array of a string, with the specified P and MOD.
     * @param str the string
     * @param P the char weight in Rabin Karp algorithm
     * @param MOD the mod in Rabin Karp algorithm
     * @return the hash array
     */
    static long[] getHash(String str, long P, long MOD) {
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
        long[] newWeight1 = new long[newLength + 1];
        long[] newWeight2 = new long[newLength + 1];
        for (int i = 0; i <= maxStringLength; ++i) {
            newWeight1[i] = weight1[i];
            newWeight2[i] = weight2[i];
        }
        for (int i = maxStringLength; i < newLength; ++i) {
            newWeight1[i + 1] = newWeight1[i] * P1 % MOD1;
            newWeight2[i + 1] = newWeight2[i] * P2 % MOD2;
        }
        weight1 = newWeight1;
        weight2 = newWeight2;
        maxStringLength = newLength;
    }
}
