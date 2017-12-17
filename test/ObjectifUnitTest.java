
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class ObjectifUnitTest {

    @Test
    public void testObjectifsPanda(){
        Joueur j=Mockito.mock(Joueur.class);

        Objectif o=new Objectif(1);
        Mockito.when(j.getBambous()).thenReturn(new int[]{2,0,0}, new int[]{0,0,0});
        Assert.assertEquals(o.appliqueObjectif(j, null), 3);
        Assert.assertEquals(o.appliqueObjectif(j, null), 0);

        Mockito.when(j.getBambous()).thenReturn(new int[]{0,0,2}, new int[]{0,0,0});
        o=new Objectif(2);
        Assert.assertEquals(o.appliqueObjectif(j, null), 4);
        Assert.assertEquals(o.appliqueObjectif(j, null), 0);

        Mockito.when(j.getBambous()).thenReturn(new int[]{0,2,0}, new int[]{0,0,0});
        o=new Objectif(3);
        Assert.assertEquals(o.appliqueObjectif(j, null), 5);
        Assert.assertEquals(o.appliqueObjectif(j, null), 0);

        Mockito.when(j.getBambous()).thenReturn(new int[]{1,1,1}, new int[]{0,0,0});
        o=new Objectif(4);
        Assert.assertEquals(o.appliqueObjectif(j, null), 6);
        Assert.assertEquals(o.appliqueObjectif(j, null), 0);
    }


    @Test
    public void testObjectifsJardinier(){
        Joueur j=Mockito.mock(Joueur.class);
        Plateau p=new Plateau();


        Objectif o=new Objectif(5);
        Assert.assertEquals(o.appliqueObjectif(j, p), 0);

        Parcelle parc=Mockito.mock(Parcelle.class);
        Mockito.when(parc.getColor()).thenReturn(2);
        Mockito.when(parc.getAmenagement()).thenReturn(3);
        Mockito.when(parc.getNbBambou()).thenReturn(4);
        p.getParcelles().add(parc);

        Assert.assertEquals(o.appliqueObjectif(j, p), 5);


        o=new Objectif(7);
        Assert.assertEquals(o.appliqueObjectif(j, p), 0);

        parc=Mockito.mock(Parcelle.class);
        Mockito.when(parc.getColor()).thenReturn(1);
        Mockito.when(parc.getNbBambou()).thenReturn(3);
        p.getParcelles().add(parc);
        parc=Mockito.mock(Parcelle.class);
        Mockito.when(parc.getColor()).thenReturn(1);
        Mockito.when(parc.getNbBambou()).thenReturn(3);
        p.getParcelles().add(parc);

        Assert.assertEquals(o.appliqueObjectif(j, p), 6);

    }


    @Test
    public void testObjectifsParcelle(){
        Joueur j=Mockito.mock(Joueur.class);
        Plateau p=new Plateau();

        Objectif o=new Objectif(20);
        Assert.assertEquals(o.appliqueObjectif(j, p), 0);


        Parcelle parc= new Parcelle(0);
        p.addParcelle(parc, 350,215);

        parc= new Parcelle(0);
        p.addParcelle(parc, 450,215);

        parc= new Parcelle(0);
        parc.setIrriguee();
        p.addParcelle(parc, 400,135);

        Assert.assertEquals(o.appliqueObjectif(j, p), 2);

    }





}
