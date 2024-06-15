package org.example.annotation.Impl;



import java.util.List;
import java.util.Optional;
public interface JpaExecutor<T> {
    Optional<T> findById(Number id);
    String  add(T t );

}
