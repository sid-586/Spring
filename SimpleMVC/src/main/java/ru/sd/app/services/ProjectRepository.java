package ru.sd.app.services;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> retreiveAll();

    void store(T item);

    boolean removeItem(T itemToRemove);
}
