
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class JoueurUnitTest {

    @Test
    public void testVerificationObjectifsAvecUnObjectifRealise(){
        Model m = new Model(2);
        Joueur j=new Joueur(0);

        j.getObjectifs().clear();
        Objectif obj=Mockito.mock(Objectif.class);
        Mockito.when(obj.appliqueObjectif(j,m.getPlateau())).thenReturn(3);
        j.getObjectifs().add(obj);
        j.addObjectifTest(0);

        j.verificationObjectifs(m);


        Assert.assertEquals(j.getPoints(), 3);
        Assert.assertEquals(j.getObjectifs().size(), 0);
        Assert.assertEquals(j.getNbObjectifsRealises(), 1);
    }

    @Test
    public void testVerificationObjectifsSansObjectifRealise(){
        Model m = new Model(2);
        Joueur j=new Joueur(0);

        j.getObjectifs().clear();
        Objectif obj=Mockito.mock(Objectif.class);
        Mockito.when(obj.appliqueObjectif(j,m.getPlateau())).thenReturn(0);
        j.getObjectifs().add(obj);
        j.addObjectifTest(0);

        j.verificationObjectifs(m);


        Assert.assertEquals(j.getPoints(), -1);
        Assert.assertEquals(j.getObjectifs().size(), 1);
        Assert.assertEquals(j.getNbObjectifsRealises(), 0);
    }
}
