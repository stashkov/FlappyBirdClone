package utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils
{

    private ShaderUtils()
    {
    }

    public static int load( String vertPath, String fragPath )
    {
        String vertex = FileUtils.loadAsString( vertPath );
        String fragment = FileUtils.loadAsString( fragPath );
        return create( vertex, fragment );
    }

    private static int create( String vertex, String fragment )
    {
        int program = glCreateProgram();
        int vertexID = glCreateShader( GL_VERTEX_SHADER );
        int fragmentID = glCreateShader( GL_FRAGMENT_SHADER );
        glShaderSource( vertexID, vertex );
        glShaderSource( fragmentID, fragment );

        if ( isCompileFailed( "vertex", vertexID ) ) return -1;
        if ( isCompileFailed( "fragment", fragmentID ) ) return -1;

        glAttachShader( program, vertexID );
        glAttachShader( program, fragmentID );
        glLinkProgram( program );
        glValidateProgram( program );

        glDeleteShader( vertexID );
        glDeleteShader( fragmentID );

        return program;
    }

    private static boolean isCompileFailed( String shaderType, int shaderTypeID )
    {
        glCompileShader( shaderTypeID );
        if ( glGetShaderi( shaderTypeID, GL_COMPILE_STATUS ) == GL_FALSE )
        {
            System.err.println( "Failed to compile " + shaderType + " shader!" );
            System.err.println( glGetShaderInfoLog( shaderTypeID ) );
            return true;
        }
        return false;
    }

}
