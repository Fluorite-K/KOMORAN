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
package kr.co.shineware.nlp.komoran.core;

import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import kr.co.shineware.nlp.komoran.util.ElapsedTimeChecker;
import kr.co.shineware.util.common.file.FileUtil;
import kr.co.shineware.util.common.model.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KomoranTest {

	private Komoran komoran;
	@Before
	public void init() throws Exception {
		this.komoran = new Komoran("models_light");
	}

	@Test
	public void speedTest() throws Exception {
		Komoran komoran = new Komoran("models_light");
//		komoran.setFWDic("komoran_benchmarker/fwd2.user");
		int count = 100;
		int avgElapsedTime = 0;
		while(true){
			BufferedReader br = new BufferedReader(new FileReader("stress.test"));
			String line = null;
			long begin,end;
			long elapsedTime=0l;
			while((line = br.readLine()) != null){
//				String[] tmp = line.split(" ");
//				for (String t : tmp) {
//					begin = System.currentTimeMillis();
//					komoran.analyze(t);
//					end = System.currentTimeMillis();
//					elapsedTime += (end-begin);
//				}
//				tmp = null;
				begin = System.currentTimeMillis();
				komoran.analyze(line);
				end = System.currentTimeMillis();
				elapsedTime += (end-begin);

			}
			br.close();
			System.out.println(elapsedTime);
			avgElapsedTime += elapsedTime;
//			TimeChecker.printElapsedTimeRatio(acc);
			if(count-- == 0)break;
		}
		System.out.println("Avg. ElapsedTime : "+avgElapsedTime/100);
	}

	@Test
	public void singleThreadSpeedTest() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("analyze_result.txt"));

		List<String> lines = FileUtil.load2List("user_data/wiki.titles");
		List<KomoranResult> komoranList = new ArrayList<>();

		long begin = System.currentTimeMillis();

		int count = 0;

		for (String line : lines) {

			komoranList.add(this.komoran.analyze(line));
			if (komoranList.size() == 1000) {
				for (KomoranResult komoranResult : komoranList) {
					bw.write(komoranResult.getPlainText());
					bw.newLine();
				}
				komoranList.clear();
			}
			count++;
			if (count % 10000 == 0) {
				System.out.println(count);
			}
		}

		for (KomoranResult komoranResult : komoranList) {
			bw.write(komoranResult.getPlainText());
			bw.newLine();
		}

		long end = System.currentTimeMillis();

		bw.close();

		System.out.println("Elapsed time : " + (end - begin));
		ElapsedTimeChecker.printTimes();
	}

	@Test
	public void analyze() throws Exception {
		KomoranResult komoranResult = this.komoran.analyze("자주 걸렸던 병이다");
		List<Pair<String,String>> pairList = komoranResult.getList();
		for (Pair<String, String> morphPosPair : pairList) {
			System.out.println(morphPosPair);
		}

		List<String> nounList = komoranResult.getNouns();
		for (String noun : nounList) {
			System.out.println(noun);
		}

		System.out.println(komoranResult.getPlainText());

		List<Token> tokenList = komoranResult.getTokenList();
		for (Token token : tokenList) {
			System.out.println(token);
		}
	}

	@Test
	public void load() throws Exception {
		this.komoran.load("models_full");
	}

	@Test
	public void setFWDic() throws Exception {
		this.komoran.setFWDic("user_data/fwd.user");
		this.komoran.analyze("감사합니다! 바람과 함께 사라지다는 진짜 재밌었어요! nice good!");
	}

	@Test
	public void setUserDic() throws Exception {
		this.komoran.setUserDic("user_data/dic.user");
		System.out.println(this.komoran.analyze("싸이는 가수다").getPlainText());
	}

}