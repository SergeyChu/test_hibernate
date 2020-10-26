package services;

import hibernateEntities.Identifiable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {

    private final Map<Integer, Identifiable> cache;

    public Cache(int pCacheSize){

        cache = new LinkedHashMap<Integer, Identifiable>(pCacheSize, .75F, true) {
            //Removes entry after a new one is added
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > pCacheSize;
            }
        };

    }

    public void add(Identifiable pEntity) {
        if (!cache.containsKey(pEntity.getId())){
            cache.put(pEntity.getId(), pEntity);
        }
    }

    public void delete(Integer pId){
        cache.remove(pId);
    }

    public void update(Integer pId, Identifiable pEntity){
        cache.put(pId, pEntity);
    }

    public Identifiable getByID(Integer pId) {
        return cache.get(pId);
    }

    public int getSize() {
        return cache.size();
    }

}
