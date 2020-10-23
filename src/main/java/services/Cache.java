package services;

import hibernateEntities.Identifiable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<T extends Identifiable> {

    private Map<Integer, T> cache;

    public Cache(int pCacheSize){

        cache = new LinkedHashMap<Integer, T>(pCacheSize, .75F, true) {
            //Removes entry after a new one is added
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > pCacheSize;
            }
        };

    }

    public void add(T pEntity) {
        if (!cache.containsKey(pEntity.getId())){
            cache.put(pEntity.getId(), pEntity);
        }
    }

    public void delete(Integer pId){
        cache.remove(pId);
    }

    public void update(Integer pId, T pEntity){
        cache.put(pId, pEntity);
    }

    public T getByID(Integer pId) {
        return cache.get(pId);
    }

    public int getSize() {
        return cache.size();
    }

}
