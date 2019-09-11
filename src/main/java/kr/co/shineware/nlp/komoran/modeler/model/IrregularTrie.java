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

import kr.co.shineware.ds.aho_corasick.AhoCorasickDictionary;
import kr.co.shineware.ds.trie.trie.doublearray.ahocorasick.AhoCorasickDoubleArrayTrie;
import kr.co.shineware.nlp.komoran.interfaces.FileAccessible;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IrregularTrie implements FileAccessible{
	private AhoCorasickDoubleArrayTrie<List<IrregularNode>> dic;
	private AhoCorasickDictionary<List<IrregularNode>> tempDic;
	
	public IrregularTrie(){
		this.init();
	}

	public void init(){
		this.dic = null;
		this.dic = new AhoCorasickDoubleArrayTrie<>();
		tempDic = new AhoCorasickDictionary<>();
	}

	public void put(String irr,IrregularNode irrNode){
		List<IrregularNode> irrNodeList = this.tempDic.getValue(irr);
		if(irrNodeList == null){
			irrNodeList = new ArrayList<>();
			irrNodeList.add(irrNode);
		}else{
			boolean hasSameNode = false;
			for (IrregularNode irregularNode : irrNodeList) {
				if(irrNode.equals(irregularNode)){
					hasSameNode = true;
					break;
				}
			}
			if(!hasSameNode){
				irrNodeList.add(irrNode);
			}
		}
		this.dic.put(irr, irrNodeList);
		tempDic.put(irr, irrNodeList);
	}
	
	public AhoCorasickDoubleArrayTrie<List<IrregularNode>> getTrieDictionary(){
		return dic;
	}

	@Override
	public void save(String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			dic.save(oos);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(String filename) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
			dic.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load(File file) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			dic.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void load(InputStream is) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			dic.load(ois);
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
