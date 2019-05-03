package Messaging.Mapping;

public interface ObjectToStringMapper {
    String map(Object o);
    boolean isMappable(Object player);

    class ObjectNotMappableToString extends RuntimeException {}
}
