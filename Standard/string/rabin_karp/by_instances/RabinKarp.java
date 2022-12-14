//import java.util.*;

/**
 * A class that helps to use Rabin Karp algorithm.
 * The public methods are advised.
 * Instantiate object for strings, then call methods with them, this solution compares 2 different hash arrays, is the safest.
 * All the methods have static and non-static types.
 * 
 * @author cary61
 */

class RabinKarp {

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
     * Prepare the weight array, initial length of 200, before using.
     */
    static {
        weight1[0] = 1;
        weight2[0] = 1;
    }

    /**
     * The first hash array.
     */
    long[] hashA;

    /**
     * The second hash array.
     */
    long[] hashB;

    /**
     * Construct the RabinKarpString instance.
     * @param str the string that will be converted to RabinKarpString
     * @return the RabinKarpString refers to original string
     */
    public static RabinKarp of(String str) {
        return new RabinKarp(str);
    }

    /**
     * Find the index of the string needle in string haystack by their RabinKarpStrings, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinKarpString of string that will be searched
     * @param needleRabin the RabinKarpString of string that should be found
     * @return the index of needle in haystack, if non-existed, -1
     */
    public static int indexOf(RabinKarp haystackRabin, RabinKarp needleRabin) {
        return indexOf(haystackRabin, 0, haystackRabin.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }
    
    /**
     * Find the index of string needle in string haystack by their RabinKarpStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinKarpString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleRabin the RabinKarpString of string that should be found
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinKarp haystackRabin, int fromIndex, RabinKarp needleRabin) {
        return indexOf(haystackRabin, fromIndex, haystackRabin.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }

    /**
     * Find the index of the part of string needle in string haystack by their RabinKarpStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinKarpString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param needleRabin the RabinKarpString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinKarp haystackRabin, int fromIndex, RabinKarp needleRabin, int beginIndex, int endIndex) {
        return indexOf(haystackRabin, fromIndex, haystackRabin.hashA.length - 2, needleRabin, beginIndex, endIndex);
    }

    /**
     * Find the index of the part of string needle in part of string haystack by their RabinKarpStrings, if non-existed, return -1.
     * 
     * @param haystackRabin the RabinKarpString of string that will be searched
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needleRabin the RabinKarpString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public static int indexOf(RabinKarp haystackRabin, int fromIndex, int toIndex, RabinKarp needleRabin, int beginIndex, int endIndex) {
        int idx1 = indexOf(haystackRabin.hashA, fromIndex, toIndex, needleRabin.hashA, beginIndex, endIndex, P1, MOD1, weight1);
        if (idx1 == -1) return -1;
        int idx2 = indexOf(haystackRabin.hashB, fromIndex, toIndex, needleRabin.hashB, beginIndex, endIndex, P2, MOD2, weight2);
        return idx1 == idx2 ? idx1 : -1;
    }

    /**
     * Check if one string matches another by their RabinKarpStrings.
     * 
     * @param str1 the RabinKarpString of first string
     * @param str2 the RabinKarpString of second string
     * @return if they are matched
     */
    public static boolean match(RabinKarp str1, RabinKarp str2) {
        return match(str1, 0, str1.hashA.length - 2, str2, 0, str2.hashA.length - 2);
    }

    /**
     * Check if str1[beginIndex : endIndex] matches str2 by their RabinKarpStrings.
     * 
     * @param str1 the RabinKarpString of first string
     * @param beginIndex the lower bound of index of the first string
     * @param endIndex the upper bound of index of the first string
     * @param str2 the RabinKarpString of second string
     * @return if they are matched
     */
    public static boolean match(RabinKarp str1, int beginIndex, int endIndex, RabinKarp str2) {
        return match(str1, beginIndex, endIndex, str2, 0, str2.hashA.length - 2);
    }

    /**
     * Check if str1 matches str2[beginIndex : endIndex] by their RabinKarpStrings.
     * 
     * @param str1 the RabinKarpString of first string
     * @param str2 the RabinKarpString of second string
     * @param beginIndex the lower bound of index of the second string
     * @param endIndex the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(RabinKarp str1, RabinKarp str2, int beginIndex, int endIndex) {
        return match(str1, 0, str1.hashA.length - 2, str2, beginIndex, endIndex);
    }
 
    /**
     * Check if str1[beginIndex1 : endIndex1] matches str2[beginIndex2 : endIndex2] by their RabinKarpStrings.
     * 
     * @param str1 the RabinKarpString of first string
     * @param beginIndex1 the lower bound of index of the first string
     * @param endIndex1 the upper bound of index of the first string
     * @param str2 the RabinKarpString of second string
     * @param beginIndex2 the lower bound of index of the second string
     * @param endIndex2 the upper bound of index of the second string
     * @return if they are matched
     */
    public static boolean match(RabinKarp str1, int beginIndex1, int endIndex1, RabinKarp str2, int beginIndex2, int endIndex2) {
        return match(str1.hashA, beginIndex1, endIndex1, str2.hashA, beginIndex2, endIndex2, P1, MOD1, weight1)
            && match(str1.hashB, beginIndex1, endIndex1, str2.hashB, beginIndex2, endIndex2, P2, MOD2, weight2);
    }

    /**
     * Find the index of the string needle in string haystack by their RabinKarpStrings, if non-existed, return -1.
     * 
     * @param needleRabin the RabinKarpString of string that should be found
     * @return the index of needle in haystack, if non-existed, -1
     */
    public int indexOf(RabinKarp needleRabin) {
        return indexOf(0, this.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }

    /**
     * Find the index of string needle in string haystack by their RabinKarpStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param beginIndex the lower bound of index of the first string
     * @param endIndex the upper bound of index of the first string
     * @param str the RabinKarpString of second string
     * @return if they are matched
     * @return
     */
    public int indexOf(int fromIndex, RabinKarp needleRabin) {
        return indexOf(fromIndex, this.hashA.length - 2, needleRabin, 0, needleRabin.hashA.length - 2);
    }

    /**
     * Find the index of the part of string needle in string haystack by their RabinKarpStrings, starting the search at the specified index, if non-existed, return -1.
     * 
     * @param fromIndex the lower bound of index of haystack
     * @param needleRabin the RabinKarpString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public int indexOf(int fromIndex, RabinKarp needleRabin, int beginIndex, int endIndex) {
        return indexOf(fromIndex, this.hashA.length - 2, needleRabin, beginIndex, endIndex);
    }

    /**
     * Find the index of the part of string needle in part of string haystack by their RabinKarpStrings, if non-existed, return -1.
     * 
     * @param fromIndex the lower bound of index of haystack
     * @param toIndex the upper bound of index of haystack
     * @param needleRabin the RabinKarpString of string that should be found
     * @param beginIndex the lowebound of index of needle
     * @param endIndex the upper bound of index of needle
     * @return the index of part of needle in part of haystack, if non-existed, -1
     */
    public int indexOf(int fromIndex, int toIndex, RabinKarp needleRabin, int beginIndex, int endIndex) {
        int idx1 = indexOf(this.hashA, fromIndex, toIndex, needleRabin.hashA, beginIndex, endIndex, P1, MOD1, weight1);
        if (idx1 == -1) return -1;
        int idx2 = indexOf(this.hashB, fromIndex, toIndex, needleRabin.hashB, beginIndex, endIndex, P2, MOD2, weight2);
        return idx1 == idx2 ? idx1 : -1;
    }

    /**
     * Check if this string matches another by their RabinKarpStrings.
     * 
     * @param str the RabinKarpString of another string
     * @return if they are matched
     */
    public boolean match(RabinKarp str) {
        return match(0, this.hashA.length - 2, str, 0, str.hashA.length - 2);
    }

    /**
     * Check if this string[beginIndex : endIndex] matches another string by their RabinKarpStrings.
     * 
     * @param beginIndex the lower bound of index of the this string
     * @param endIndex the upper bound of index of the this string
     * @param str the RabinKarpString of another string
     * @return if they are matched
     */
    public boolean match(int beginIndex, int endIndex, RabinKarp str) {
        return match(beginIndex, endIndex, str, 0, str.hashA.length - 2);
    }

    /**
     * Check if this string matches str2[beginIndex : endIndex] by their RabinKarpStrings.
     * 
     * @param str the RabinKarpString of another string
     * @param beginIndex the lower bound of index of the another string
     * @param endIndex the upper bound of index of the another string
     * @return if they are matched
     */
    public boolean match(RabinKarp str, int beginIndex, int endIndex) {
        return match(0, this.hashA.length - 2, str, beginIndex, endIndex);
    }

    /**
     * Check if this string[beginIndex1 : endIndex1] matches another string[beginIndex2 : endIndex2] by their RabinKarpStrings.
     * 
     * @param beginIndex1 the lower bound of index of the this string
     * @param endIndex1 the upper bound of index of the this string
     * @param str the RabinKarpString of another string
     * @param beginIndex2 the lower bound of index of the another string
     * @param endIndex2 the upper bound of index of the another string
     * @return if they are matched
     */
    public boolean match(int beginIndex1, int endIndex1, RabinKarp str, int beginIndex2, int endIndex2) {
        return match(this.hashA, beginIndex1, endIndex1, str.hashA, beginIndex2, endIndex2, P1, MOD1, weight1)
            && match(this.hashB, beginIndex1, endIndex1, str.hashB, beginIndex2, endIndex2, P2, MOD2, weight2);
    }





    // Internal Implementations




    
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
    static int indexOf(long[] hash1, int fromIndex, int toIndex, long[] hash2, int beginIndex, int endIndex, long P, long MOD, long[] weight) {
        int n =  endIndex - beginIndex + 1;
        if (toIndex - fromIndex + 1 < n) {
            return -1;
        }
        long weightValue = weight[n];
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
    static boolean match(long[] hash1, int beginIndex1, int endIndex1, long[] hash2, int beginIndex2, int endIndex2, long P, long MOD, long[] weight) {
        int n = endIndex1 - beginIndex1 + 1;
        if (n != endIndex2 - beginIndex2 + 1) {
            return false;
        }
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

    RabinKarp(String str) {
        this.hashA = getHash(str, P1, MOD1);
        this.hashB = getHash(str, P2, MOD2);
    }
}
