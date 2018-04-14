package graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import maths.Matrix4f;
import maths.Vector3f;
import utils.ShaderUtils;

public class Shader
{

    public static final int VERTEX_ATTRIBUTE = 0; // attributes are set every single vertex
    public static final int TEXTURE_COORDINATE_ATTRIBUTE = 1;

    public static Shader BACKGROUND; // we don't want to have more than one instance of this Shader
    public static Shader BIRD;
    public static Shader PIPE;
    public static Shader FADE;

    private boolean enabled = false;

    private final int ID;
    private Map<String, Integer> locationCache = new HashMap<>();

    public Shader( String vertex, String fragment )
    {
        ID = ShaderUtils.load( vertex, fragment );
    }

    public void enable()
    {
        glUseProgram( ID );
        enabled = true;
    }

    public void disable()
    {
        glUseProgram( 0 );
        enabled = false;
    }

    public void setUniform1i( String name, int value )
    {
        if ( !enabled ) enable();
        glUniform1i( getUniform( name ), value );
    }

    public void setUniform1f( String name, float value )
    {
        if ( !enabled ) enable();
        glUniform1f( getUniform( name ), value );
    }

    public void setUniform2f( String name, float x, float y )
    {
        if ( !enabled ) enable();
        glUniform2f( getUniform( name ), x, y );
    }

    public void setUniform3f( String name, Vector3f vector )
    {
        if ( !enabled ) enable();
        glUniform3f( getUniform( name ), vector.x, vector.y, vector.z );
    }

    public void setUniformMat4f( String name, Matrix4f matrix )
    {
        if ( !enabled ) enable();
        glUniformMatrix4fv( getUniform( name ), false, matrix.toFloatBuffer() );
    }

    public int getUniform( String name )
    {
        // optimization, because we will be rendering many times a second
        if ( locationCache.containsKey( name ) )
        {
            return locationCache.get( name );
        }
        int result = glGetUniformLocation( ID, name );
        if ( result == -1 )
        {
            System.err.println( "Could not find uniform variable " + name );
        }
        else
        {
            locationCache.put( name, result );
        }
        return result;
    }

    public static void loadAll()
    {
        BACKGROUND = new Shader( "shaders/bg.vert", "shaders/bg.frag" );
        BIRD = new Shader( "shaders/bird.vert", "shaders/bird.frag" );
        PIPE = new Shader( "shaders/pipe.vert", "shaders/pipe.frag" );
        FADE = new Shader( "shaders/fade.vert", "shaders/fade.frag" );
    }
}
