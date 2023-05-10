public class SipHash {

    public SipHash() {
        this.cROUNDS = 2;
        this.dROUNDS = 4;
    }

    /**
     * Hash an entire string.
     * @param s    the string to hash
     * @return  the SipHash 64-bit hash value of the input string
     */
    public long hash(String s) {
        char[] in = s.toCharArray();
        return hash(in, in.length);
    }

    private final int cROUNDS;  // SipHash round parameter
    private final int dROUNDS;  // SipHash round parameter

    private long v0, v1, v2, v3;  // internal 256-bit SipHash state

    /**
     * Initialize the internal state.
     */
    private void init() {
        v0 = 0x736f6d6570736575L;
        v1 = 0x646f72616e646f6dL;
        v2 = 0x6c7967656e657261L;
        v3 = 0x7465646279746573L;
    }

    /**
     * Left-rotate the 64-bit long value v by b positions.
     * @param   v   a 64-bit long value
     * @param   b   the left-rotation displacement
     * @return  the 64-bot long value v left-rotated by b positions
     */
    private static long ROTL(long v, int b) {
        return (v << b) | (v >>> (64 - b));
    }

    /**
     * The looped SipHash round function.
     * @param rounds    the number of rounds to apply the core round function
     */
    private void SipRounds(int rounds) {
        for (int r = 0; r < rounds; r++) {
            v0 += v1; v1 = ROTL(v1, 13); v1 ^= v0; v0 = ROTL(v0, 32);
            v2 += v3; v3 = ROTL(v3, 16); v3 ^= v2;
            v0 += v3; v3 = ROTL(v3, 21); v3 ^= v0;
            v2 += v1; v1 = ROTL(v1, 17); v1 ^= v2; v2 = ROTL(v2, 32);
        }
    }

    /**
     * Hash a prefix of a char array.
     *
     * @param in    the char buffer containing the input to hash
     * @param inlen length of the array section to hash
     * @return the SipHash 64-bit hash value of that section
     */
    private long hash(char[] in, int inlen) {
        init();
        // hash the input in whole 4-char chunks:
        for (int j = 0; j + 4 <= inlen; j += 4) {
            // map sequences of four consecutive 16-bit Unicode chars to 64-bit longs:
            long chunk =
                    (((long)in[j    ] & 0xFFFFL)      ) |
                            (((long)in[j + 1] & 0xFFFFL) << 16) |
                            (((long)in[j + 2] & 0xFFFFL) << 32) |
                            (((long)in[j + 3] & 0xFFFFL) << 48);
            v3 ^= chunk;
            SipRounds(cROUNDS);
            v0 ^= chunk;
        }
        // length-pad and hash the remaining < 4 chars (if any):
        long b = ((long)inlen) << 56;  // length-based padding
        int pos = inlen;
        switch (inlen & 3) {  // Duff's device to process 0-3 values
            case 3: b |= ((long)in[--pos] & 0xFFFFL) << 32;
            case 2: b |= ((long)in[--pos] & 0xFFFFL) << 16;
            case 1: b |= ((long)in[--pos] & 0xFFFFL);
            case 0: break;
        }
        v3 ^= b;
        SipRounds(cROUNDS);
        v0 ^= b;
        // finalize the hash computation:
        v2 ^= 0xFF;
        SipRounds(dROUNDS);
        b = v0 ^ v1 ^ v2 ^ v3;
        return b;
    }

}
