package kr.co.shineware.nlp.komoran.core;

import kr.co.shineware.nlp.komoran.corpus.builder.CorpusBuilder;
import kr.co.shineware.nlp.komoran.modeler.builder.ModelBuilder;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

public class Training {
    @Test
    @Ignore
    public void training() {
//        CorpusBuilder corpusBuilder = new CorpusBuilder();
//        corpusBuilder.setExclusiveIrrRule("resources/irrDic.remove.txt");
//        corpusBuilder.buildPath("tagged_corpus", "tag");
//        corpusBuilder.save("corpus_build");

        ModelBuilder modelBuilder = new ModelBuilder();
//        modelBuilder.setExternalDic("user_data" + File.separator + "wiki.titles");
//        modelBuilder.buildPath("corpus_build");
//        modelBuilder.save("models_full");

        modelBuilder = new ModelBuilder();
        modelBuilder.buildPath("corpus_build");
        modelBuilder.save("models_light_dat");

    }
}
