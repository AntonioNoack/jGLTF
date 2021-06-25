package de.javagl.jgltf.model.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.function.Consumer;

/**
 * A SettableBeanProperty that passes all calls to a delegate, and
 * passes error information to a consumer of {@link JsonError}s
 * (for example, when calling a setter caused an exception).
 * This is used for error reporting in the Jackson bean deserializers.
 */
class ErrorReportingSettableBeanProperty extends SettableBeanProperty {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 7398743799397469737L;

    /**
     * The delegate
     */
    private final SettableBeanProperty delegate;

    /**
     * The consumer for {@link JsonError}s
     */
    private final Consumer<? super JsonError> jsonErrorConsumer;

    /**
     * Creates a new instance with the given delegate and error consumer
     *
     * @param delegate          The delegate
     * @param jsonErrorConsumer The consumer for {@link JsonError}s. If
     *                          this is <code>null</code>, then errors will be ignored.
     */
    ErrorReportingSettableBeanProperty(
            SettableBeanProperty delegate,
            Consumer<? super JsonError> jsonErrorConsumer) {
        super(delegate);
        this.delegate = delegate;
        this.jsonErrorConsumer = jsonErrorConsumer;
    }

    @Override
    public SettableBeanProperty
    withValueDeserializer(JsonDeserializer<?> deser) {
        return new ErrorReportingSettableBeanProperty(
                delegate.withValueDeserializer(deser), jsonErrorConsumer);
    }

    @Override
    public SettableBeanProperty withName(PropertyName newName) {
        return new ErrorReportingSettableBeanProperty(
                delegate.withName(newName), jsonErrorConsumer);
    }

    @Override
    public Object setAndReturn(Object instance, Object value)
            throws IOException {
        return delegate.setAndReturn(instance, value);
    }

    @Override
    public void set(Object instance, Object value) throws IOException {
        delegate.set(instance, value);
    }

    @Override
    public AnnotatedMember getMember() {
        return delegate.getMember();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return delegate.getAnnotation(acls);
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser p,
                                          DeserializationContext ctxt, Object instance) throws IOException {
        return delegate.deserializeSetAndReturn(p, ctxt, instance);
    }

    @Override
    public void deserializeAndSet(JsonParser p, DeserializationContext ctxt,
                                  Object instance) throws IOException {
        try {
            delegate.deserializeAndSet(p, ctxt, instance);
        } catch (Exception e) {
            if (jsonErrorConsumer != null) {
                jsonErrorConsumer.accept(new JsonError(
                        e.getMessage(), p.getParsingContext(), e));
            }
        }
    }

    @Override
    public SettableBeanProperty withNullProvider(NullValueProvider nva) {
        return new ErrorReportingSettableBeanProperty(
                delegate.withNullProvider(nva), jsonErrorConsumer);
    }
}
