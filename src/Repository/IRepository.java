package Repository;
import Model.HasId;
import java.util.List;

public interface IRepository<T extends HasId> {
    void create(T obj);
    T get(Integer id);
    void update(T obj);
    void delete(Integer id);
    List<T> getAll();
}
