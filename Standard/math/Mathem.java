/**
 * Support some mathematics calculating method.
 * 
 * @author cary61
 */
class Mathem {

    /**
     * Store the preconditioning factorial.
     */
    static long[] factorial = new long[21];

    /**
     * Precondition
     */
    static {
        factorial[0] = 1;
        for (int i = 1; i <= 20; ++i) {
            factorial[i] = i * factorial[i - 1];
        }
    }

    /**
     * Find the middle number of 2 number.
     * 
     * @param a the first number
     * @param b the second number
     * @return the middle number
     */
    static int mid(int a, int b) {
        return (a & b) + ((a ^ b) >> 1);
    }

    /**
     * Find the greatest common divisor of 2 number.
     * 
     * @param a the first number
     * @param b the second number
     * @return the greatest common divisor
     */
    static int gcd(int a, int b) {
        int m;
        while (b != 0) {
            m = a % b;
            a = b;
            b = m;
        }
        return a;
    }

    /**
     * Find the least common multiple of 2 numbers.
     * 
     * @param a the first number
     * @param b the second number
     * @return the least common
     */
    static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    /**
     * Divide number a by number b, get the result after ceiling.
     * 
     * @param a the first number
     * @param b the second number
     * @return the ceiling result of division
     */
    static int ceil(int a, int b) {
        return (a - 1) / b + 1;
    }



    // Long version



    /**
     * Find the middle number of 2 number.
     * 
     * @param a the first number
     * @param b the second number
     * @return the middle number
     */
    static long mid(long a, long b) {
        return (a & b) + ((a ^ b) >> 1);
    }
    
    /**
     * Find the greatest common divisor of 2 number.
     * 
     * @param a the first number
     * @param b the second number
     * @return the greatest common divisor
     */
    static long gcd(long a, long b) {
        long m;
        while (b != 0) {
            m = a % b;
            a = b;
            b = m;
        }
        return a;
    }

    /**
     * Find the least common multiple of 2 numbers.
     * 
     * @param a the first number
     * @param b the second number
     * @return the least common
     */
    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    /**
     * Divide number a by number b, get the result after ceiling.
     * 
     * @param a the first number
     * @param b the second number
     * @return the ceiling result of division
     */
    static long ceil(long a, long b) {
        return (a - 1) / b + 1;
    }
}