package IO;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;


    public MyCompressorOutputStream(OutputStream out) throws IOException {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException
    {
        out.write((byte)b);
    }

    @Override
    public void write(byte[] b) throws IOException
    {
        int[] intArray=new int[b.length];
        int glbCounter=0;
        int i;
        for(i=0;i<12;i++){
            intArray[glbCounter]=b[i];glbCounter++;
        }
        boolean oneEnd=false;
        int countOne=0;
        int countZero=0;
        while(i<b.length) {
            while(i<b.length && b[i]==0) {
                countZero++;i++;
                if(countZero>255) {
                    intArray[glbCounter]=255; glbCounter++;
                    intArray[glbCounter]=0; glbCounter++;
                    countZero=1;
                }
            }
            intArray[glbCounter]=countZero; glbCounter++;
            countZero=0;
            while(i<b.length && b[i]==1) {
                oneEnd=true;
                countOne++;
                i++;
                if((countOne>255)) {
                    intArray[glbCounter]=255; glbCounter++;
                    intArray[glbCounter]=0; glbCounter++;
                    countOne=1;
                }
            }
            if (oneEnd) {
                intArray[glbCounter]=countOne;
                glbCounter++;
                oneEnd=false;
            }
            countOne=0;
        }
        byte[] uncompressedArray=new byte[glbCounter];
        for(int j=0;j<uncompressedArray.length;j++)
            uncompressedArray[j]=(byte)intArray[j];

        Deflater def=new Deflater();
        def.setInput(uncompressedArray);
        def.finish();
        byte[] compressedArray=new byte[b.length];
        int compressLength=def.deflate(compressedArray);
        def.end();
        for(int k=0;k<compressLength;k++)
            out.write(compressedArray[k]);

    }

}
