package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileUtils
{

    private FileUtils()
    {
    }

    static String loadAsString( String file )
    {
        StringBuilder result = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( file ) );
            String buffer;
            while ( ( buffer = reader.readLine() ) != null )
            {
                result.append( buffer ).append( '\n' );
            }
            reader.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return result.toString();
    }

}
