package kr.co.shineware.nlp.komoran.core;

import kr.co.shineware.nlp.komoran.corpus.builder.CorpusBuilder;
import org.junit.Test;

/**
 * Created by shin285 on 2017. 6. 21..
 */
public class BuilderTest {
    @Test
    public void buildCorpus(){
        CorpusBuilder builder = new CorpusBuilder();
        builder.setExclusiveIrrRule("resources/irrDic.remove.txt");
        builder.buildPath("corpus", "tag");
        builder.save("corpus_build");
    }

    @Test
    public void buildModel(){

    }
}
