package com.o2o.action.server.app;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.google.gson.JsonArray;
import com.o2o.action.server.util.CommonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class internApp extends DialogflowApp {
	/*
	public static void main(String[] args) {
		String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/json/Mgiscampinginfoseoul/1/5/";
		apiController api = new apiController();
		String st = api.get(urlStr);
		System.out.print(st);

		JsonParser jason = new JsonParser();
		JsonObject obj = (JsonObject)jason.parse(st);

		JsonArray parse_listArr = (JsonArray)obj.get("row");
	//	System.out.print(parse_listArr.get(0).toString());

	//	JsonObject objs =  parse_listArr.get(0).getAsJsonObject();

		//System.out.println(objs.get("COT_ADDR_FULL_NEW"));
		//*for(int i = 0; i < parse_listArr.size(); i++){
		//	JsonObject camp = (JsonObject)parse_listArr.get(i);
		//	String address = String.valueOf(camp.get("COT_ADDR_FULL_NEW"));
		//}*/

	@ForIntent("Default Welcome Intent")
	public ActionResponse defaultWelcome(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("안녕하세요, 테스트앱 입니다.\n무엇을 도와드릴까요?")
				.setDisplayText("안녕하세요, 테스트앱 입니다.\n무엇을 도와드릴까요?")
		;
		basicCard
				.setTitle("베이직 카드 제목")
				.setFormattedText("테스트용 베이직 카드")
				.setImage(new Image().setUrl("https://actions.o2o.kr/content/aiperson.gif")
						.setAccessibilityText("home"));

		rb.add(simpleResponse);
		rb.add(basicCard);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("Next")
	public ActionResponse Next(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("안녕")
				.setDisplayText("안녕하세요")
		;

		basicCard
				.setTitle("Title : 강아지")
				.setSubtitle("귀여워")
				.setImage(
						new Image()
						.setUrl("https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile7.uf.tistory.com%2Fimage%2F24283C3858F778CA2EFABE")
						.setAccessibilityText("강아지"))
				.setButtons(
						new ArrayList<Button>(
								Arrays.asList(
										new Button()
											.setTitle("하이")
											.setOpenUrlAction(
													new OpenUrlAction().setUrl("https://www.naver.com/")
											)
								)
						)
				)
		;

		rb.add(simpleResponse);
		rb.add(basicCard);
		rb.add("which response would you like to see next?");

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("Menu")
	public ActionResponse Menu(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("메뉴를 선택해주세요")
				.setDisplayText("메뉴를 선택해주세요")
		;


		rb.add(simpleResponse);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("conclusion")
	public ActionResponse conclusion(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		String menu = CommonUtil.makeSafeString(request.getParameter("Menu"));
		SimpleResponse simpleResponse = new SimpleResponse();

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech(menu + "선택하셨습니다.");

		rb.add(simpleResponse);

		return rb.build();
	}

	@ForIntent("CampingSiteSelection")
	public ActionResponse Camping_Site_Selection(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		SelectionList selectionlist = new SelectionList();

		simpleResponse.setTextToSpeech("안녕하세요, 캠핑장을 선택해주세요.")
				.setDisplayText("안녕하세요, 캠핑장을 선택해주세요.")
		;

//		selectionlist
//				.setTitle("캠핑장 선택")
//				.setItems(
//						Arrays.asList(
//								new ListSelectListItem()
//										.setTitle("난지 캠핑장")
//										.setImage(
//												new Image()
//														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/nanji_pic01.jpg")
//														.setAccessibilityText("난지캠핑장"))
//										.setOptionInfo(
//												new OptionInfo()
//														.setSynonyms(
//																Arrays.asList("난지","난지 캠핑")
//														)
//														.setKey("SELECTION_KEY_ONE")),
//								new ListSelectListItem()
//										.setTitle("노을 캠핑장")
//										.setImage(
//												new Image()
//														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/noel_pic01.jpg")
//														.setAccessibilityText("노을캠핑장"))
//										.setOptionInfo(
//												new OptionInfo()
//														.setKey("SELECTION_KEY_TWO"))
//
//						)
//
//				);

		selectionlist
				.setTitle("캠핑장 선택")
				.setItems(
						Arrays.asList(
								new ListSelectListItem()
										.setTitle("Nanji 캠핑장")
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/nanji_pic01.jpg")
														)
										.setOptionInfo(
												new OptionInfo()
														.setKey("SELECTION_KEY_ONE")),
								new ListSelectListItem()
										.setTitle("Noel 캠핑장")
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/noel_pic01.jpg")
														)
										.setOptionInfo(
												new OptionInfo()
														.setKey("SELECTION_KEY_TWO"))

						)

				);

		//String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/xml/Mgiscampinginfoseoul/1/5/";
		//apiController api = new apiController();
		//String st = api.get(urlStr);

		rb.add(simpleResponse);
		rb.add(selectionlist);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("periodselection")
	public ActionResponse periodselection(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		String selectedCamp = CommonUtil.makeSafeString(request.getParameter("camp"));
		SimpleResponse simpleResponse = new SimpleResponse();

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech(selectedCamp+"를 선택하셨습니다.\n 기간을 선택해주세요")
				.setDisplayText(selectedCamp+"를 선택하셨습니다.\n 기간을 선택해주세요")

		;
		rb.addSuggestions(new String[]{"1박 2일","2박 3일","3박 4일"});

		rb.add(simpleResponse);

		return rb.build();
	}

	@ForIntent("SelectDate")
	public ActionResponse SelectDate(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		String period = CommonUtil.makeSafeString(request.getParameter("period"));
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech("날짜를 선택해주세요.\n 머무시는 기간은 "+period+" 입니다.")
				.setDisplayText("날짜를 선택해주세요.\n 머무시는 기간은 "+period+" 입니다.")
		;

		basicCard
				.setTitle("날짜를 선택해주세요")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/cal.PNG"));

		rb.add(simpleResponse);
		rb.add(basicCard);

		return rb.build();
	}

	@ForIntent("selectfacilites")
	public ActionResponse selectfacilites(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		String selectedItem = request.getSelectedOption();

		String selectedDate = CommonUtil.makeSafeString(request.getParameter("date"));
		String date2 = selectedDate.substring(0,10);

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech("시설을 선택해주세요.\n 선택하신 날짜는 "+date2+"입니다.")
				.setDisplayText("시설을 선택해주세요.\n 선택하신 날짜는 "+date2+"입니다.")
				.setDisplayText("item? "+selectedItem)
		;

//		if(selectedItem.equals("SELECTION_KEY_ONE")){
//			basicCard
//					.setTitle("난지")
//					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/NanjiFacilities.PNG"));
//			rb.add(basicCard);
//		}else if(selectedItem.equals("SELECTION_KEY_TWO")){
//			basicCard
//					.setTitle("노을")
//					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/Noelfacilities.PNG"));
//			rb.add(basicCard);
//		}

		//Training phase 더 다양하게 구성
		// + 캠핑장 선택할 때 2번씩 호출되는 이유
		//다양한 시설을 조건을 어떻게 해야할지 selectedItem = null인데 두개의 인텐트를 어떻게 연결하는지

		rb.add(simpleResponse);

		return rb.build();
	}
}
