package com.tom.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.constants.ResumeAnalyzerConstants;
import com.tom.dao.UIMADao;
import com.tom.exception.ResumeAnalyzerServiceException;

@Service
public class UIMAServiceImpl implements UIMAService{
	@Autowired
	private TikaService tikaService;
	
	@Autowired
	private UIMADao uimaDao;


	@Override
	public void saveParsedResumeJson(String contextPath) throws ResumeAnalyzerServiceException, AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, IOException {
		// getting list of string from raw cv format
		List<String> parsedResumes=tikaService.parseRawFiles(ResumeAnalyzerConstants.CVANALYZER_FILE_UPLOAD_LOCATION);

		//parsing and storing in mongo
		analyzeAndStore(parsedResumes,contextPath);
	}

	private void analyzeAndStore(List<String> parsedResumes,String contextPath) throws AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, IOException
	{
		System.out.println("Inside analyze and store cv");

		JSONArray jsonArray = new JSONArray();
		for(String singleResume : parsedResumes){
			JSONObject jsonObj = analyzeResume(singleResume,contextPath);
			if (!jsonObj.isEmpty()) {
				jsonArray.add(jsonObj);
			}
		}
		//saving the json array to db
		uimaDao.saveParsedJsons(jsonArray);
	}

	public JSONObject analyzeResume (String unstructuredData,String contextPath) throws 
	IOException, InvalidXMLException, ResourceInitializationException, 
	AnalysisEngineProcessException{
		// get Resource Specifier from XML file
		//accessing resource file to detect name descriptor
		File file=new File("/Users/i319267/Downloads/greyhr/descriptoprs/ResumeAnalysisPOSAnnotator.xml");
		XMLInputSource in = new XMLInputSource(file);
		ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);

		// create Analysis Engine
		AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(specifier);
		// create a CAS
		CAS cas = ae.newCAS();
		//processing the files
		return processFile(unstructuredData, ae, cas);
	}

	private static JSONObject processFile(String aFile, AnalysisEngine aAE, CAS aCAS) throws IOException,
	AnalysisEngineProcessException {

		String document =aFile;
		document = document.trim();
		// put document text in CAS
		aCAS.setDocumentText(document);
		// process
		aAE.process(aCAS);
		// print annotations to System.out
		JSONObject jsonObj = getStructuredJSON(aCAS);
		// reset the CAS to prepare it for processing the next document
		aCAS.reset();
		return jsonObj;
	}

	public static JSONObject getStructuredJSON(CAS aCAS) {
		// get iterator over annotations
		FSIterator iter = aCAS.getAnnotationIndex().iterator();
		JSONObject json = new JSONObject();
		// iterate
		while (iter.isValid()) {
			FeatureStructure fs = iter.get();
			getPosTags(fs, aCAS, 0, json);
			iter.moveToNext();
		}
		return json;
	}

	public static void getPosTags (FeatureStructure aFS, CAS aCAS, int aNestingLevel, JSONObject json)
	{
		String annotationKey = aFS.getType().getName();
		if (json.get(annotationKey) == null) {
			if (!annotationKey.equals(ResumeAnalyzerConstants.TCAS_ANNOTATION_KEY)) {
				if (aFS instanceof AnnotationFS) {
					AnnotationFS annot = (AnnotationFS) aFS;
					String coveredText = annot.getCoveredText();
					if (annotationKey.equals(ResumeAnalyzerConstants.WORK_EXPERIENCE_KEY)) {
						coveredText = stringCleanUp (coveredText, "([\\d]{1,})?.?[\\d]{1,}");
					}
					if (annotationKey.equals(ResumeAnalyzerConstants.PIN_CODE_KEY)) {
						coveredText = stringCleanUp (coveredText, "[\\d]{6}");
					}
					if (coveredText instanceof String) {
						coveredText = processString(coveredText);
					}
					if (annotationKey.equals(ResumeAnalyzerConstants.WORK_EXPERIENCE_KEY)) {
						json.put(aFS.getType().getName(), Float.parseFloat(coveredText));
					}
					else {
						json.put(annotationKey, coveredText);
					}
				}
			}
		}
		else if ((aFS.getType().getName()).equals(ResumeAnalyzerConstants.OTHER_NAMED_TAGS_KEY) ) {
			if (aFS instanceof AnnotationFS) {
				AnnotationFS annot = (AnnotationFS) aFS;
				String coveredText = annot.getCoveredText();
				coveredText = processString(coveredText);
				appendOtherNamedTags(json, coveredText);
			}
		}
	}

	public static String processString (String coveredText)
	{
		coveredText = coveredText.replaceAll("\\n|\\r", "");
		coveredText = coveredText.trim();
		return coveredText;
	}

	public static String stringCleanUp (String coveredText, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(coveredText);
		StringBuffer result = new StringBuffer();
		while (match.find()) {
			coveredText = match.group();
		}
		return coveredText;
	}

	public static void appendOtherNamedTags (JSONObject json, String coveredText)
	{
		String otherNamedTags = (String) json.get(ResumeAnalyzerConstants.OTHER_NAMED_TAGS_KEY);
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(otherNamedTags).add(coveredText);
		String joinedString = joiner.toString();
		json.put(ResumeAnalyzerConstants.OTHER_NAMED_TAGS_KEY, joinedString);
	}

}
