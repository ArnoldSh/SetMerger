import java.util.Map;
import java.util.Set;

public interface SetMerger<T, V> {

    Set<Set<V>> merge(Set<Set<V>> superset);
    Map<T, Set<V>> merge(Map<T, Set<V>> supermap);

}
