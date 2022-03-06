// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////

package com.unique.blockchain.nft.infrastructure.other;


import com.subgraph.orchid.encoders.Base64;
import com.subgraph.orchid.encoders.Hex;

import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.util.Strings;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class Bech32 {
    /**
     * The Bech32 character set for encoding.
     */
    private static final String CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l";

    /**
     * The Bech32 character set for decoding.
     */
    private static final byte[] CHARSET_REV = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            15, -1, 10, 17, 21, 20, 26, 30, 7, 5, -1, -1, -1, -1, -1, -1,
            -1, 29, -1, 24, 13, 25, 9, 8, 23, -1, 18, 22, 31, 27, 19, -1,
            1, 0, 3, 16, 11, 28, 12, 14, 6, 4, 2, -1, -1, -1, -1, -1,
            -1, 29, -1, 24, 13, 25, 9, 8, 23, -1, 18, 22, 31, 27, 19, -1,
            1, 0, 3, 16, 11, 28, 12, 14, 6, 4, 2, -1, -1, -1, -1, -1
    };

    public static class Bech32Data {
        final String hrp;
        final byte[] data;

        private Bech32Data(final String hrp, final byte[] data) {
            this.hrp = hrp;
            this.data = data;
        }

        public byte[] getData() {
            return this.data;
        }
    }

    /**
     * Find the polynomial with value coefficients mod the generator as 30-bit.
     */
    private static int polymod(final byte[] values) {
        int c = 1;
        for (byte v_i : values) {
            int c0 = (c >>> 25) & 0xff;
            c = ((c & 0x1ffffff) << 5) ^ (v_i & 0xff);
            if ((c0 & 1) != 0) c ^= 0x3b6a57b2;
            if ((c0 & 2) != 0) c ^= 0x26508e6d;
            if ((c0 & 4) != 0) c ^= 0x1ea119fa;
            if ((c0 & 8) != 0) c ^= 0x3d4233dd;
            if ((c0 & 16) != 0) c ^= 0x2a1462b3;
        }
        return c;
    }

    /**
     * Expand a HRP for use in checksum computation.
     */
    private static byte[] expandHrp(final String hrp) {
        int hrpLength = hrp.length();
        byte ret[] = new byte[hrpLength * 2 + 1];
        for (int i = 0; i < hrpLength; ++i) {
            int c = hrp.charAt(i) & 0x7f; // Limit to standard 7-bit ASCII
            ret[i] = (byte) ((c >>> 5) & 0x07);
            ret[i + hrpLength + 1] = (byte) (c & 0x1f);
        }
        ret[hrpLength] = 0;
        return ret;
    }

    /**
     * Verify a checksum.
     */
    private static boolean verifyChecksum(final String hrp, final byte[] values) {
        byte[] hrpExpanded = expandHrp(hrp);
        byte[] combined = new byte[hrpExpanded.length + values.length];
        System.arraycopy(hrpExpanded, 0, combined, 0, hrpExpanded.length);
        System.arraycopy(values, 0, combined, hrpExpanded.length, values.length);
        return polymod(combined) == 1;
    }

    /**
     * Create a checksum.
     */
    private static byte[] createChecksum(final String hrp, final byte[] values) {
        byte[] hrpExpanded = expandHrp(hrp);
        byte[] enc = new byte[hrpExpanded.length + values.length + 6];
        System.arraycopy(hrpExpanded, 0, enc, 0, hrpExpanded.length);
        System.arraycopy(values, 0, enc, hrpExpanded.length, values.length);
        int mod = polymod(enc) ^ 1;
        byte[] ret = new byte[6];
        for (int i = 0; i < 6; ++i) {
            ret[i] = (byte) ((mod >>> (5 * (5 - i))) & 31);
        }
        return ret;
    }

    /**
     * Encode a Bech32 string.
     */
    public static String encode(final Bech32Data bech32) {
        return encode(bech32.hrp, bech32.data);
    }

    /**
     * Encode a Bech32 string.
     */
    public static String encode(String hrp, final byte[] values) {
        checkArgument(hrp.length() >= 1, "Human-readable part is too short");
        checkArgument(hrp.length() <= 83, "Human-readable part is too long");
        hrp = hrp.toLowerCase(Locale.ROOT);
        byte[] checksum = createChecksum(hrp, values);
        byte[] combined = new byte[values.length + checksum.length];
        System.arraycopy(values, 0, combined, 0, values.length);
        System.arraycopy(checksum, 0, combined, values.length, checksum.length);
        StringBuilder sb = new StringBuilder(hrp.length() + 1 + combined.length);
        sb.append(hrp);
        sb.append('1');
        for (byte b : combined) {
            sb.append(CHARSET.charAt(b));
        }
        return sb.toString();
    }

    /**
     * Decode a Bech32 string.
     */
    public static Bech32Data decode(final String str) throws AddressFormatException {
        boolean lower = false, upper = false;
        if (str.length() < 8)
            throw new AddressFormatException.InvalidDataLength("Input too short: " + str.length());
        if (str.length() > 90)
            throw new AddressFormatException.InvalidDataLength("Input too long: " + str.length());
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c < 33 || c > 126) throw new AddressFormatException.InvalidCharacter(c, i);
            if (c >= 'a' && c <= 'z') {
                if (upper)
                    throw new AddressFormatException.InvalidCharacter(c, i);
                lower = true;
            }
            if (c >= 'A' && c <= 'Z') {
                if (lower)
                    throw new AddressFormatException.InvalidCharacter(c, i);
                upper = true;
            }
        }
        final int pos = str.lastIndexOf('1');
        if (pos < 1) throw new AddressFormatException.InvalidPrefix("Missing human-readable part");
        final int dataPartLength = str.length() - 1 - pos;
        if (dataPartLength < 6)
            throw new AddressFormatException.InvalidDataLength("Data part too short: " + dataPartLength);
        byte[] values = new byte[dataPartLength];
        for (int i = 0; i < dataPartLength; ++i) {
            char c = str.charAt(i + pos + 1);
            if (CHARSET_REV[c] == -1) throw new AddressFormatException.InvalidCharacter(c, i + pos + 1);
            values[i] = CHARSET_REV[c];
        }
        String hrp = str.substring(0, pos).toLowerCase(Locale.ROOT);
        if (!verifyChecksum(hrp, values)) throw new AddressFormatException.InvalidChecksum();
        return new Bech32Data(hrp, Arrays.copyOfRange(values, 0, values.length - 6));
    }

    public static byte[] convert(byte[] data, int inBits, int outBits, boolean pad) {
        int value = 0;
        int bits = 0;
        int maxV = (1 << outBits) - 1;
        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < data.length; ++i) {
            int r = (data[i]);
            if (r < 0) {
                r = (data[i] + 256);
            }
            value = (value << inBits) | r;
            bits += inBits;

            while (bits >= outBits) {
                bits -= outBits;
                int b = value >> bits;
                int a = (b) & maxV;
                result.add((byte) a);
            }
        }

        if (pad) {
            if (bits > 0) {
                result.add((byte) ((value << (outBits - bits)) + maxV));
            }
        } else {
            if (bits >= inBits) return null;
            if ((value << (outBits - bits)) + maxV == 0) return null;
        }
//
        byte[] bytes = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            bytes[i] = result.get(i);
        }
        return bytes;
    }

    public static String getPubFromAddress2(String address) {
        try {
            Bech32Data data = Bech32.decode(address);
            byte[] bbb = convert(data.getData(), 5, 8, false);
            if (bbb.length == 37) {
                byte[] b4 = new byte[32];
                System.arraycopy(bbb, 5, b4, 0, 32);
                return Strings.fromByteArray(Base64.encode(b4));
            } else if (bbb.length == 38) {
                byte[] b4 = new byte[33];
                System.arraycopy(bbb, 5, b4, 0, 33);
                return Strings.fromByteArray(Base64.encode(b4));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getPubFromAddress(String address) {
        Map<String, String> map = new HashMap<>();
        try {
            Bech32Data data = Bech32.decode(address);
            byte[] bbb = convert(data.getData(), 5, 8, false);
            if (bbb.length == 37) {
                byte[] b4 = new byte[32];
                System.arraycopy(bbb, 5, b4, 0, 32);
                String value = Strings.fromByteArray(Base64.encode(b4));
                map.put("type", "tendermint/PubKeyEd25519");
                map.put("value", value);
                return map;
            } else if (bbb.length == 38) {
                byte[] b4 = new byte[33];
                System.arraycopy(bbb, 5, b4, 0, 33);
                String value = Strings.fromByteArray(Base64.encode(b4));
                map.put("type", "tendermint/PubKeySecp256k1");
                map.put("value", value);
                return map;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public static String generateValidatorAddressFromPub(String pubKey, String mainPrefix) {
        try {
            String addr = createNewAddressSecp256k1(mainPrefix, Hex.decode(pubKey));
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey) throws Exception {
        String addressResult = null;
        try {
            byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
            byte[] address = ripemd160(pubKeyHash);
            byte[] bytes = encode(0, address);
            addressResult = Bech32.encode(mainPrefix, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressResult;

    }
    public static byte[] ripemd160(byte[]... args) {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[160 / 8];
        digest.doFinal(out, 0);
        return out;
    }
    private static byte[] sha256Hash(byte[] input, int offset, int length) throws NoSuchAlgorithmException {
        byte[] result = new byte[32];
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input, offset, length);
        return digest.digest();
    }

    private static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
        byte[] convertedProgram = convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
        return convertedProgram;
    }
    public static byte[] convertBits(final byte[] in, final int inStart, final int inLen, final int fromBits,
                                     final int toBits, final boolean pad) throws AddressFormatException {
        int acc = 0;
        int bits = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream(64);
        final int maxv = (1 << toBits) - 1;
        final int max_acc = (1 << (fromBits + toBits - 1)) - 1;
        for (int i = 0; i < inLen; i++) {
            int value = in[i + inStart] & 0xff;
            if ((value >>> fromBits) != 0) {
                throw new AddressFormatException(
                        String.format("Input value '%X' exceeds '%d' bit size", value, fromBits));
            }
            acc = ((acc << fromBits) | value) & max_acc;
            bits += fromBits;
            while (bits >= toBits) {
                bits -= toBits;
                out.write((acc >>> bits) & maxv);
            }
        }
        if (pad) {
            if (bits > 0)
                out.write((acc << (toBits - bits)) & maxv);
        } else if (bits >= fromBits || ((acc << (toBits - bits)) & maxv) != 0) {
            throw new AddressFormatException("Could not convert bits, invalid padding");
        }
        return out.toByteArray();
    }
}
