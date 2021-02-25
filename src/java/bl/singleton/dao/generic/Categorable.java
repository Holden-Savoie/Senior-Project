package bl.singleton.dao.generic;
public interface Categorable<T extends CharSequence>
{
    public T getType();
    public void setType( T t );
}