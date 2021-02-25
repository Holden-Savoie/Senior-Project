package bl.singleton.dao.generic;
public interface Keyable<K>
{
    public K getKey();
    public void setKey( K key);
}