package utils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils
{
    private ShaderUtils()
    {
    }

    public static int load( String vertexShaderPath, String fragmentShaderPath )
    {
        String vertex = FileUtils.loadAsString( vertexShaderPath );
        String fragment = FileUtils.loadAsString( fragmentShaderPath );
        return create( vertex, fragment );
    }

    private static int create( String vertex, String fragment )
    {
        int program = glCreateProgram();
        int vertexID = glCreateShader( GL_VERTEX_SHADER );
        int fragmentID = glCreateShader( GL_FRAGMENT_SHADER );
        glShaderSource( vertexID, vertex );
        glShaderSource( fragmentID, fragment );
        glCompileShader( vertexID );
        if ( checkSuccessfulCompilation( vertexID, "vertex" ) ) return -1;
        glCompileShader( fragmentID );
        if ( checkSuccessfulCompilation( fragmentID, "fragment" ) ) return -1;

        glAttachShader( program, vertexID );
        glAttachShader( program, fragmentID );
        glLinkProgram( program );
        glValidateProgram( program );

        glDeleteShader( vertexID );
        glDeleteShader( fragmentID );

        return program;
    }

    private static boolean checkSuccessfulCompilation( int shaderID, String message )
    {
        message = "Failed to compile " + message + " shader!";
        if ( glGetShaderi( shaderID, GL_COMPILE_STATUS ) == GL_FALSE )
        {
            System.err.println( message );
            System.err.println( glGetShaderInfoLog( shaderID ) );
            return true;
        }
        return false;
    }


}
