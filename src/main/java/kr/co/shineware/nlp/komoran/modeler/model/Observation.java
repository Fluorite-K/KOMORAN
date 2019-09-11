/*******************************************************************************
 * KOMORAN 3.0 - Korean Morphology Analyzer
 *
 * Copyright 2015 Shineware http://www.shineware.co.kr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 	
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package kr.co.shineware.nlp.komoran.modeler.model;

import kr.co.shineware.ds.trie.trie.doublearray.ahocorasick.AhoCorasickDoubleArrayTrie;
import kr.co.shineware.nlp.komoran.interfaces.FileAccessible;
import kr.co.shineware.nlp.komoran.interfaces.UnitParser;
import kr.co.shineware.nlp.komoran.model.ScoredTag;
import kr.co.shineware.nlp.komoran.parser.KoreanUnitParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Observation implements FileAccessible{

	private AhoCorasickDoubleArrayTrie<List<ScoredTag>> observation;
	private UnitParser parser;
	
	public Observation(){
		this.init();
	}
	
	private void init() {
		this.observation = new AhoCorasickDoubleArrayTrie<>();
		this.parser = new KoreanUnitParser();
	}

	public void put(String word, String tag, int tagId, double observationScore) {
		String koreanUnits = parser.parse(word);
		this.observation
		List<ScoredTag> scoredTagList = this.observation.getValue(koreanUnits);
		if(scoredTagList == null){
			scoredTagList = new ArrayList<>();
			scoredTagList.add(new ScoredTag(tag, tagId, observationScore));
		}else{
			int i=0;
			for(i=0;i<scoredTagList.size();i++){
				if(scoredTagList.get(i).getTagId() == tagId){
					break;
				}
			}
			if(scoredTagList.size() == i){
				scoredTagList.add(new ScoredTag(tag, tagId, observationScore));
			}
		}
		this.observation.put(koreanUnits, scoredTagList);
	}
	
	public AhoCorasickDoubleArrayTrie<List<ScoredTag>> getTrieDictionary(){
		return observation;
	}

	@Override
	public void save(String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			observation.save(oos);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(String filename) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
			observation.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load(File file) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			observation.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void load(InputStream is) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			observation.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
