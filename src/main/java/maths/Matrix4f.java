package maths;

import java.nio.FloatBuffer;

import utils.BufferUtils;

@SuppressWarnings( "PointlessArithmeticExpression" )
public class Matrix4f
{

    private static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    private Matrix4f()
    {

    }

    public static Matrix4f identity()
    {
        Matrix4f result = new Matrix4f();
        for ( int i = 0; i < SIZE; i++ )
        {
            result.elements[i] = 0.0f;
        }
        result.elements[0 + 0 * SIZE] = 1.0f;
        result.elements[1 + 1 * SIZE] = 1.0f;
        result.elements[2 + 2 * SIZE] = 1.0f;
        result.elements[3 + 3 * SIZE] = 1.0f;

        return result;
    }

    /**
     * In orthograpic projection objects that are further away do not look smaller
     */
    public static Matrix4f orthographic( float left,
                                         float right,
                                         float bottom,
                                         float top,
                                         float near,
                                         float far )
    {
        Matrix4f result = identity();

        result.elements[0 + 0 * SIZE] = 2.0f / ( right - left );

        result.elements[1 + 1 * SIZE] = 2.0f / ( top - bottom );

        result.elements[2 + 2 * SIZE] = 2.0f / ( near - far );

        result.elements[0 + 3 * SIZE] = ( left + right ) / ( left - right );
        result.elements[1 + 3 * SIZE] = ( bottom + top ) / ( bottom - top );
        result.elements[2 + 3 * SIZE] = ( far + near ) / ( far - near );

        return result;
    }

    public static Matrix4f translate( Vector3f vector )
    {
        Matrix4f result = identity();
        result.elements[0 + 3 * SIZE] = vector.x;
        result.elements[1 + 3 * SIZE] = vector.y;
        result.elements[2 + 3 * SIZE] = vector.z;
        return result;
    }

    /**
     * We do not need to rotate in 3D, we only need to rotate by z
     * Which is 2D rotation
     *
     * @param angle
     * @return
     */
    public static Matrix4f rotate( float angle )
    {
        Matrix4f result = identity();
        float r = ( float ) Math.toRadians( angle );
        float cos = ( float ) Math.cos( r );
        float sin = ( float ) Math.sin( r );

        result.elements[0 + 0 * SIZE] = cos;
        result.elements[1 + 0 * SIZE] = sin;

        result.elements[0 + 1 * SIZE] = -sin;
        result.elements[1 + 1 * SIZE] = cos;

        return result;
    }

    public Matrix4f multiply( Matrix4f matrix )
    {
        Matrix4f result = new Matrix4f();
        for ( int y = 0; y < SIZE; y++ )
        {
            for ( int x = 0; x < SIZE; x++ )
            {
                float sum = 0.0f;
                for ( int e = 0; e < SIZE; e++ )
                {
                    sum += this.elements[x + e * SIZE] * matrix.elements[e + y * SIZE];
                }
                result.elements[x + y * SIZE] = sum;
            }
        }
        return result;
    }

    public FloatBuffer toFloatBuffer()
    {
        return BufferUtils.createFloatBuffer( elements );
    }

}
