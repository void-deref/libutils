package fun.libutils;


public class Utils
{
    public void
	xor(byte[] buff, short off, short len)
	{
        short bound = (short)(off + len);

		for (short i = off; i < bound; ++i) {
			buff[i] ^= (short)0xFF;
		}
	}

	public void
	bubble_sort(byte[] buff, short off, short len)
	{
		byte  temp = 0;
		short curr = 0;
		short next = 0;

		if (len == 1) {
			return;
		}
		
		short outer_len = (short)(off + len - 1);
		for (short k = off; k < outer_len; ++k) {

			short inner_len = (short)(outer_len - (short)(k - off));
			for (short i = off; i < inner_len; ++i) {
				
				// eliminating an issue that originates from Java-signess, which
				// leands to a situation where '0x80 (-127)' is less than '0x01'.
				curr = (short)(buff[i] & 0xFF);
				next = (short)(buff[(short)(i + 1)] & 0xFF);
				if (curr <= next) {
					continue;
				}
	
				temp    = (byte)curr;
				buff[i] = (byte)next;
				buff[(short)(i + 1)] = temp;
			}
		}
	}

    public void
    from_bcd(byte[] buff, short off, short len)
    {
        short bound = (short)(off + len);
        byte _byte  = 0;
        byte msn = 0;
        byte lsn = 0;

        for ( ; off < bound; ++off) {
            _byte = buff[off];
			
            msn = (byte)((_byte & 0xF0) >>> 4);
            lsn = (byte) (_byte          << 4);
            buff[off] = (byte)(lsn | msn);
        }

        // fetch the LSN of the last byte
        _byte = buff[(short)(off - 1)];
        lsn   = (byte)(_byte & 0x0F);

        // discard trailing 'f' (if any)
        if (lsn == 0x0F) {
            buff[(short)(off - 1)] = (byte)(_byte & 0xF0);
        }
    }
}
