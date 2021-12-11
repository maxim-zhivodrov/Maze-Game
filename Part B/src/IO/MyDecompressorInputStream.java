package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] compressedArray=new byte[b.length];
        int reader=-2;
        int k=0;
        while((reader=in.read())!=-1){
            compressedArray[k]=(byte)reader;
            k++;
        }
        Inflater inf=new Inflater();
        inf.setInput(compressedArray,0,k);
        byte[] decompressedArray=new byte[b.length];
        int decompressLength=-1;
        try {
            decompressLength=inf.inflate(decompressedArray);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        inf.end();

        int counter=0;
        int i=0;
        for(i=0;i<12;i++){
            b[i]=decompressedArray[counter]; counter++;
        }
        boolean isZero=true;
        while(i<b.length&&counter<decompressLength){
            int number=decompressedArray[counter];
            counter++;
            if(number<0)
                number=number+256;
            if(isZero){
                for(int j=0;j<number&&i<b.length;j++){
                    b[i]=0; i++;
                }
                isZero=false;
            }
            else{
                for(int j=0;j<number&&i<b.length;j++){
                    b[i]=1; i++;
                }
               isZero=true;
            }
        }
        return 0;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}

