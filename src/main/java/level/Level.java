package level;

import graphics.Shader;
import graphics.VertexArray;

public class Level
{
    private VertexArray background;

    public Level()
    {
        float[] vertices = new float[]
                {
                        -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                        -10.0f, 10.0f * 9.0f / 16.0f, 0.0f,
                        0.0f, 10.0f * 9.0f / 16.0f, 0.0f,
                        0.0f, -10.0f * 9.0f / 16.0f, 0.0f,
                };
        //indices re-use the same vertices
        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        // what the actual coordinates are
        float[] textureCoordinates = new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        background = new VertexArray( vertices, indices, textureCoordinates );
    }

    public void render()
    {
        Shader.BACKGROUND.enable();
        background.render();
        Shader.BACKGROUND.disable();

    }


}
