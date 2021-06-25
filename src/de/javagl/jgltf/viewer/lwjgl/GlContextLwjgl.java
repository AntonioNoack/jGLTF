package de.javagl.jgltf.viewer.lwjgl;

import de.javagl.jgltf.logging.Logger;
import de.javagl.jgltf.model.GltfConstants;
import de.javagl.jgltf.viewer.GlContext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_BASE_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.glBlendColor;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Implementation of a {@link GlContext} based on LWJGL
 */
public class GlContextLwjgl implements GlContext {

    public static int version = 330;

    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(GlContextLwjgl.class);

    /**
     * A buffer that will be used temporarily for the values of
     * integer uniforms. This is a direct buffer that is created
     * and resized as necessary in {@link #putIntBuffer(int[])}
     */
    private IntBuffer uniformIntBuffer = null;

    /**
     * A buffer that will be used temporarily for the values of
     * float uniforms. This is a direct buffer that is created
     * and resized as necessary in {@link #putFloatBuffer(float[])}
     */
    private FloatBuffer uniformFloatBuffer = null;

    /**
     * Put the given values into a direct IntBuffer and return it.
     * The returned buffer may always be a slice of the same instance.
     * This method is supposed to be called only from the OpenGL thread.
     *
     * @param value The value
     * @return The IntBuffer
     */
    private IntBuffer putIntBuffer(int[] value) {
        int total = value.length;
        if (uniformIntBuffer == null || uniformIntBuffer.capacity() < total) {
            uniformIntBuffer = ByteBuffer
                    .allocateDirect(total * Integer.BYTES)
                    .order(ByteOrder.nativeOrder())
                    .asIntBuffer();
        }
        uniformIntBuffer.position(0);
        uniformIntBuffer.limit(uniformIntBuffer.capacity());
        uniformIntBuffer.put(value);
        uniformIntBuffer.flip();
        return uniformIntBuffer;
    }

    /**
     * Put the given values into a direct IntBuffer and return it.
     * The returned buffer may always be a slice of the same instance.
     * This method is supposed to be called only from the OpenGL thread.
     *
     * @param value The value
     * @return The IntBuffer
     */
    private FloatBuffer putFloatBuffer(float[] value) {
        int total = value.length;
        if (uniformFloatBuffer == null || uniformFloatBuffer.capacity() < total) {
            uniformFloatBuffer = ByteBuffer
                    .allocateDirect(total * Float.BYTES)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
        }
        uniformFloatBuffer.position(0);
        uniformFloatBuffer.limit(uniformFloatBuffer.capacity());
        uniformFloatBuffer.put(value);
        uniformFloatBuffer.flip();
        return uniformFloatBuffer;
    }

    @Override
    public Integer createGlProgram(String vertexShaderSource, String fragmentShaderSource) {
        if (vertexShaderSource == null) {
            logger.warning("The vertexShaderSource is null");
            return null;
        }
        if (fragmentShaderSource == null) {
            logger.warning("The fragmentShaderSource is null");
            return null;
        }

        logger.fine("Creating vertex shader...");

        Integer glVertexShader = createGlShader(GL_VERTEX_SHADER, vertexShaderSource);
        if (glVertexShader == null) {
            logger.warning("Creating vertex shader FAILED");
            return null;
        }

        logger.fine("Creating vertex shader DONE");

        logger.fine("Creating fragment shader...");
        Integer glFragmentShader =
                createGlShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        if (glFragmentShader == null) {
            logger.warning("Creating fragment shader FAILED");
            return null;
        }
        logger.fine("Creating fragment shader DONE");

        int glProgram = glCreateProgram();

        glAttachShader(glProgram, glVertexShader);
        glDeleteShader(glVertexShader);

        glAttachShader(glProgram, glFragmentShader);
        glDeleteShader(glFragmentShader);

        glLinkProgram(glProgram);
        glValidateProgram(glProgram);

        int validateStatus = glGetProgrami(glProgram, GL_VALIDATE_STATUS);
        if (validateStatus != GL_TRUE) {
            printProgramLogInfo(glProgram);
            return null;
        }
        return glProgram;
    }

    /**
     * Creates an OpenGL shader with the given type, from the given source
     * code, and returns the GL shader object. If the shader cannot be
     * compiled, then <code>null</code> will be returned.
     *
     * @param shaderType   The shader type
     * @param shaderSource The shader source code
     * @return The GL shader
     */
    private Integer createGlShader(int shaderType, String shaderSource) {
        int glShader = glCreateShader(shaderType);
        if (!shaderSource.contains("#version")) {
            shaderSource = "#version " + version + "\n" + shaderSource;
        }
        glShaderSource(glShader, shaderSource);
        glCompileShader(glShader);
        int compileStatus = glGetShaderi(glShader, GL_COMPILE_STATUS);
        if (compileStatus != GL_TRUE) {
            printShaderLogInfo(glShader);
        }
        return glShader;
    }

    @Override
    public void useGlProgram(int glProgram) {
        glUseProgram(glProgram);
    }

    @Override
    public void deleteGlProgram(int glProgram) {
        glDeleteProgram(glProgram);
    }

    @Override
    public void enable(Iterable<? extends Number> states) {
        if (states != null) {
            for (Number state : states) {
                if (state != null) {
                    glEnable(state.intValue());
                }
            }
        }
    }

    @Override
    public void disable(Iterable<? extends Number> states) {
        if (states != null) {
            for (Number state : states) {
                if (state != null) {
                    glDisable(state.intValue());
                }
            }
        }
    }

    @Override
    public int getUniformLocation(int glProgram, String uniformName) {
        glUseProgram(glProgram);
        return glGetUniformLocation(glProgram, uniformName);
    }

    @Override
    public int getAttributeLocation(int glProgram, String attributeName) {
        glUseProgram(glProgram);
        return glGetAttribLocation(glProgram, attributeName);
    }

    @Override
    public void setUniformiv(int type, int location, int count, int[] value) {
        if (value == null) {
            logger.warning("Invalid uniform value: null");
            return;
        }
        switch (type) {
            case GltfConstants.GL_INT:
            case GltfConstants.GL_UNSIGNED_INT: {
                IntBuffer b = putIntBuffer(value);
                glUniform1iv(location, b);
                break;
            }
            case GltfConstants.GL_INT_VEC2: {
                IntBuffer b = putIntBuffer(value);
                glUniform2iv(location, b);
                break;
            }
            case GltfConstants.GL_INT_VEC3: {
                IntBuffer b = putIntBuffer(value);
                glUniform3iv(location, b);
                break;
            }
            case GltfConstants.GL_INT_VEC4: {
                IntBuffer b = putIntBuffer(value);
                glUniform4iv(location, b);
                break;
            }
            default:
                logger.warning("Invalid uniform type: " +
                        GltfConstants.stringFor(type));
        }
    }

    @Override
    public void setUniformfv(int type, int location, int count, float[] value) {
        if (value == null) {
            logger.warning("Invalid uniform value: null");
            return;
        }
        switch (type) {
            case GltfConstants.GL_FLOAT: {
                FloatBuffer b = putFloatBuffer(value);
                glUniform1fv(location, b);
                break;
            }
            case GltfConstants.GL_FLOAT_VEC2: {
                FloatBuffer b = putFloatBuffer(value);
                glUniform2fv(location, b);
                break;
            }
            case GltfConstants.GL_FLOAT_VEC3: {
                FloatBuffer b = putFloatBuffer(value);
                glUniform3fv(location, b);
                break;
            }
            case GltfConstants.GL_FLOAT_VEC4: {
                FloatBuffer b = putFloatBuffer(value);
                glUniform4fv(location, b);
                break;
            }
            default:
                logger.warning("Invalid uniform type: " +
                        GltfConstants.stringFor(type));
        }

    }

    @Override
    public void setUniformMatrixfv(
            int type, int location, int count, float[] value) {
        if (value == null) {
            logger.warning("Invalid uniform value: null");
            return;
        }
        switch (type) {
            case GltfConstants.GL_FLOAT_MAT2: {
                FloatBuffer b = putFloatBuffer(value);
                glUniformMatrix2fv(location, false, b);
                break;
            }
            case GltfConstants.GL_FLOAT_MAT3: {
                FloatBuffer b = putFloatBuffer(value);
                glUniformMatrix3fv(location, false, b);
                break;
            }
            case GltfConstants.GL_FLOAT_MAT4: {
                FloatBuffer b = putFloatBuffer(value);
                glUniformMatrix4fv(location, false, b);
                break;
            }
            default:
                logger.warning("Invalid uniform type: " +
                        GltfConstants.stringFor(type));
        }
    }


    @Override
    public void setUniformSampler(int location, int textureIndex, int glTexture) {
        glActiveTexture(GL_TEXTURE0 + textureIndex);
        glBindTexture(GL_TEXTURE_2D, glTexture);
        glUniform1i(location, textureIndex);
    }


    @Override
    public int createGlVertexArray() {
        return glGenVertexArrays();
    }

    @Override
    public void deleteGlVertexArray(int glVertexArray) {
        glDeleteVertexArrays(glVertexArray);
    }

    @Override
    public int createGlBufferView(
            int target, int byteLength, ByteBuffer bufferViewData) {
        int glBufferView = glGenBuffers();
        glBindBuffer(target, glBufferView);
        ByteBuffer data = bufferViewData.slice();
        data.limit(byteLength);
        glBufferData(target, bufferViewData, GL_STATIC_DRAW);
        return glBufferView;
    }

    @Override
    public void createVertexAttribute(int glVertexArray,
                                      int target, int glBufferView, int attributeLocation,
                                      int size, int type, int stride, int offset) {
        glBindVertexArray(glVertexArray);
        glBindBuffer(target, glBufferView);
        glVertexAttribPointer(
                attributeLocation, size, type, false, stride, offset);
        glEnableVertexAttribArray(attributeLocation);
    }

    @Override
    public void updateVertexAttribute(int glVertexArray,
                                      int target, int glBufferView, int offset, int size, ByteBuffer data) {
        glBindVertexArray(glVertexArray);
        glBindBuffer(target, glBufferView);
        glBufferSubData(target, offset, data);
    }

    @Override
    public void deleteGlBufferView(int glBufferView) {
        glDeleteBuffers(glBufferView);
    }

    @Override
    public int createGlTexture(
            ByteBuffer pixelData, int internalFormat,
            int width, int height, int format, int type) {
        int glTexture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, glTexture);
        glTexImage2D(
                GL_TEXTURE_2D, 0, internalFormat, width, height,
                0, format, type, pixelData);

        return glTexture;
    }

    @Override
    public void setGlTextureParameters(int glTexture,
                                       int minFilter, int magFilter, int wrapS, int wrapT) {
        glBindTexture(GL_TEXTURE_2D, glTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapT);
    }

    @Override
    public void deleteGlTexture(int glTexture) {
        glDeleteTextures(glTexture);
    }

    @Override
    public void renderIndexed(
            int glVertexArray, int mode, int glIndicesBuffer,
            int numIndices, int indicesType, int offset
    ) {
        logger.fine("Drawing " + numIndices + " elements at offset " + offset + " with mode " + mode);
        glBindVertexArray(glVertexArray);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, glIndicesBuffer);
        glDrawElements(mode, numIndices, indicesType, offset);
    }

    @Override
    public void renderNonIndexed(int glVertexArray, int mode, int numVertices) {
        logger.fine("Drawing " + numVertices + " vertices with mode " + mode);
        glBindVertexArray(glVertexArray);
        glDrawArrays(mode, 0, numVertices);
    }

    @Override
    public void setBlendColor(float r, float g, float b, float a) {
        glBlendColor(r, g, b, a);
    }

    @Override
    public void setBlendEquationSeparate(int modeRgb, int modeAlpha) {
        glBlendEquationSeparate(modeRgb, modeAlpha);
    }

    @Override
    public void setBlendFuncSeparate(
            int srcRgb, int dstRgb, int srcAlpha, int dstAlpha) {
        glBlendFuncSeparate(srcRgb, dstRgb, srcAlpha, dstAlpha);
    }

    @Override
    public void setColorMask(boolean r, boolean g, boolean b, boolean a) {
        glColorMask(r, g, b, a);
    }

    @Override
    public void setCullFace(int mode) {
        glCullFace(mode);
    }

    @Override
    public void setDepthFunc(int func) {
        glDepthFunc(func);
    }

    @Override
    public void setDepthMask(boolean mask) {
        glDepthMask(mask);
    }

    @Override
    public void setDepthRange(float zNear, float zFar) {
        glDepthRange(zNear, zFar);
    }

    @Override
    public void setFrontFace(int mode) {
        glFrontFace(mode);
    }

    @Override
    public void setLineWidth(float width) {
        glLineWidth(width);
    }

    @Override
    public void setPolygonOffset(float factor, float units) {
        glPolygonOffset(factor, units);
    }

    @Override
    public void setScissor(int x, int y, int width, int height) {
        glScissor(x, y, width, height);
    }

    /**
     * For debugging: Print shader log info
     *
     * @param id shader ID
     */
    private void printShaderLogInfo(int id) {
        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        glGetShaderiv(id, GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength.get(0) > 0) {
            infoLogLength.put(0, infoLogLength.get(0) - 1);
        }

        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0))
                .order(ByteOrder.nativeOrder());
        glGetShaderInfoLog(id, infoLogLength, infoLog);

        String infoLogString =
                StandardCharsets.US_ASCII.decode(infoLog).toString();
        if (infoLogString.trim().length() > 0) {
            logger.warning("shader log:\n" + infoLogString);
        }
    }

    /**
     * For debugging: Print program log info
     *
     * @param id program ID
     */
    private void printProgramLogInfo(int id) {
        IntBuffer infoLogLength = ByteBuffer.allocateDirect(4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        glGetProgramiv(id, GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength.get(0) > 0) {
            infoLogLength.put(0, infoLogLength.get(0) - 1);
        }

        ByteBuffer infoLog = ByteBuffer.allocateDirect(infoLogLength.get(0))
                .order(ByteOrder.nativeOrder());
        glGetProgramInfoLog(id, infoLogLength, infoLog);

        String infoLogString =
                StandardCharsets.US_ASCII.decode(infoLog).toString();
        if (infoLogString.trim().length() > 0) {
            logger.warning("program log:\n" + infoLogString);
        }
    }


}
