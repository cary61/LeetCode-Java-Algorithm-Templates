//import java.util.*;

/**
 * A class that helps to use Rabin Karp algorithm.
 * The public methods are advised.
 * Get the hash of strings, then call methods with them, this solution is suitable for several long strings.
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
     * Prepare the weight array, initial length of 200, before using.
     */
    static {
        weight[0] = 1;
    }

    /**
     * Get the hash array of a string.
     * 
     * @param str the string
     * @return the hash array
     */
    public static long[] getHash(String str) {
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
    static int indexOf(long[] hash1, int fromIndex, int toIndex, long[] hash2, int beginIndex, int endIndex) {
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
     * Check if 2 strings are matched by the hash arrays of them.
     * 
     * @param hash1 the hash array of the first string
     * @param hash2 the hash array of the second string
     * @return if they are matched
     */
    public static boolean match(long[] hash1, long[] hash2) {
        return match(hash1, 0, hash1.length - 2, hash2, 0, hash2.length - 2);
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
        return match(hash1, beginIndex, endIndex, hash2, 0, hash2.length - 2);
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
        return match(hash1, 0, hash1.length - 2, hash2, beginIndex, endIndex);
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
        int n = endIndex1 - beginIndex1 + 1;
        if (n != endIndex2 - beginIndex2 + 1) {
            return false;
        }
        return (((hash1[endIndex1 + 1] - hash1[beginIndex1] * weight[n]) % MOD + MOD) % MOD) 
            == (((hash2[endIndex2 + 1] - hash2[beginIndex2] * weight[n]) % MOD + MOD) % MOD);
    }



    // Internal Implementations

    
    
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
