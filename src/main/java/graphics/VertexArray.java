package graphics;

import utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray
{
    private final int count;
    private int vertexBufferObject;
    private int indexBufferObject;
    private int vertexArrayObject;
    private int textureBufferObject;

    //array of vertices which we send to graphics card
    //and tells it - rend those vertices here
    // and this creates a mesh - i.e. a physical shape of our object
    public VertexArray( float[] vertices, byte[] indices, float[] textureCoordinates )
    {
        count = indices.length;

        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray( vertexArrayObject );

        vertexBufferObject = glGenBuffers();
        glBindBuffer( GL_ARRAY_BUFFER, vertexBufferObject );
        glBufferData( GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer( vertices ), GL_STATIC_DRAW );
        glVertexAttribPointer( Shader.VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, 0, 0 );
        glEnableVertexAttribArray( Shader.VERTEX_ATTRIBUTE );

        textureBufferObject = glGenBuffers();
        glBindBuffer( GL_ARRAY_BUFFER, textureBufferObject );
        glBufferData( GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer( vertices ), GL_STATIC_DRAW );
        glVertexAttribPointer( Shader.TEXTURE_COORDINATE_ATTRIBUTE, 2, GL_FLOAT, false, 0, 0 );
        glEnableVertexAttribArray( Shader.TEXTURE_COORDINATE_ATTRIBUTE );

        indexBufferObject = glGenBuffers();
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, indexBufferObject );
        glBufferData( GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer( indices ), GL_STATIC_DRAW );


        glBindBuffer( GL_ARRAY_BUFFER, 0 );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );
        glBindVertexArray( 0 );

    }

    public void bind()
    {
        glBindVertexArray( vertexArrayObject );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, indexBufferObject );
    }

    public void unbind()
    {
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );
        glBindVertexArray( 0 );
    }

    public void draw()
    {
        glDrawElements( GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0 );
    }

    public void render()
    {
        bind();
        draw();
    }

}
