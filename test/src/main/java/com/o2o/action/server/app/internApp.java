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
import java.util.*;
import java.util.concurrent.ExecutionException;


public class internApp extends DialogflowApp {
	String selectedItem;
	String selectedfacilites;
	String period;
	String selectedDate;
	String seat;

//	public static void main(String[] args) {
//		String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/json/Mgiscampinginfoseoul/1/5/";
//		apiController api = new apiController();
//		String st = api.get(urlStr);
//		//System.out.print(st);
//
//		JsonParser jason = new JsonParser();
//		JsonObject obj = (JsonObject) jason.parse(st);
//
//		JsonObject objs =  obj.get("Mgiscampinginfoseoul").getAsJsonObject();
//		JsonArray array= objs.get("row").getAsJsonArray();
//		for (int i = 0; i<7; i++)
//		{
//			String sts = array.get(i).getAsJsonObject().get("COT_VALUE_02").getAsString();
//			System.out.println(sts);
//		}
//	}

	@ForIntent("Default Welcome Intent")
	public ActionResponse defaultWelcome(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("\n안녕하세요, 캠핑장 예약 서비스입니다.\n무엇을 도와드릴까요?")
				.setDisplayText("\n안녕하세요, 캠핑장 예약 서비스입니다.\n무엇을 도와드릴까요?")
		;
		basicCard
				.setImage(new Image().setUrl("http://file3.instiz.net/data/file3/2020/04/14/6/a/5/6a5cc8ef13727184b3d8af5c63de8926.jpg")
						.setAccessibilityText("home"))
				.setImageDisplayOptions("CROPPED");

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
		String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/json/Mgiscampinginfoseoul/1/9/";
		apiController api = new apiController();
		String st = api.get(urlStr);

		JsonParser jason = new JsonParser();
		JsonObject obj = (JsonObject) jason.parse(st);

		JsonObject objs =  obj.get("Mgiscampinginfoseoul").getAsJsonObject();
		JsonArray array= objs.get("row").getAsJsonArray();

		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();

		SelectionList selectionlist = new SelectionList();

		simpleResponse.setTextToSpeech("네 캠핑장 예약을 도와드리겠습니다. 예약하고 싶은 캠핑장을 말씀해주세요.")
				.setDisplayText("캠핑장 예약을 도와드리겠습니다.\n 예약하고 싶은 캠핑장을 말씀해주세요.")
		;

		selectionlist
				.setTitle("캠핑장 선택")
				.setItems(
						Arrays.asList(
								new ListSelectListItem()
										.setTitle("난지 캠핑장")
										.setDescription(array.get(4).getAsJsonObject().get("COT_ADDR_FULL_OLD").getAsString())
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/nanji_pic01.jpg")
										)
										.setOptionInfo(
												new OptionInfo()
														.setKey("난지")),
								new ListSelectListItem()
										.setTitle("노을 캠핑장")
										.setDescription(array.get(5).getAsJsonObject().get("COT_ADDR_FULL_OLD").getAsString())
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/noel_pic01.jpg")
										)
										.setOptionInfo(
												new OptionInfo()
														.setKey("노을")),
								new ListSelectListItem()
										.setTitle("서울대공원 캠핑장")
										.setDescription(array.get(6).getAsJsonObject().get("COT_ADDR_FULL_OLD").getAsString())
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/park_pic02.jpg")
										)
										.setOptionInfo(
												new OptionInfo()
														.setKey("서울대공원")),
								new ListSelectListItem()
										.setTitle("강동 그린웨이 캠핑장")
										.setDescription(array.get(8).getAsJsonObject().get("COT_ADDR_FULL_OLD").getAsString())
										.setImage(
												new Image()
														.setUrl("http://www.seoul.go.kr/res_newseoul_story/campingjang/images/gd_pic01.jpg")
										)
										.setOptionInfo(
												new OptionInfo()
														.setKey("강동"))

						)

				);

		rb.add(simpleResponse);
		rb.add(selectionlist);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("periodselection")
	public ActionResponse periodselection(ActionRequest request) throws ExecutionException, InterruptedException {
		String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/json/Mgiscampinginfoseoul/1/9/";
		apiController api = new apiController();
		String st = api.get(urlStr);

		JsonParser jason = new JsonParser();
		JsonObject obj = (JsonObject) jason.parse(st);

		JsonObject objs =  obj.get("Mgiscampinginfoseoul").getAsJsonObject();
		JsonArray array= objs.get("row").getAsJsonArray();

		ResponseBuilder rb = getResponseBuilder(request);
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		Map<String, Object> data = rb.getConversationData();
		selectedItem = request.getSelectedOption();


		simpleResponse.setTextToSpeech(selectedItem+"캠핑장을 선택하셨습니다.\n 기간을 말씀해주세요")
				.setDisplayText(selectedItem+"캠핑장을 선택하셨습니다.\n 기간을 말씀해주세요")

		;
		if(selectedItem.equals("난지")){
			basicCard
					.setTitle("난지 캠핑장")
					.setFormattedText(array.get(4).getAsJsonObject().get("COT_VALUE_01").getAsString())
					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/난지_배경.PNG"));
			rb.addSuggestions(new String[]{"1박 2일","2박 3일","3박 4일"});
			rb.add(
					new LinkOutSuggestion()
							.setDestinationName("난지 캠핑장 사이트")
							.setUrl("http://www.nanjicamp.com/")
			);
		}else if(selectedItem.equals("노을")){
			basicCard
					.setTitle("노을 캠핑장")
					.setFormattedText(array.get(5).getAsJsonObject().get("COT_VALUE_01").getAsString())
					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/노을_배경.PNG"));
			rb.addSuggestions(new String[]{"1박 2일","2박 3일","3박 4일"});
			rb.add(
					new LinkOutSuggestion()
							.setDestinationName("노을 캠핑장 사이트")
							.setUrl("http://www.seoul.go.kr/storyw/campingjang/noel.do")
			);
		}else if(selectedItem.equals("서울대공원")){
			basicCard
					.setTitle("서울대공원 캠핑장")
					.setFormattedText(array.get(6).getAsJsonObject().get("COT_VALUE_01").getAsString())
					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원배경.PNG"));
			rb.addSuggestions(new String[]{"1박 2일","2박 3일","3박 4일"});
			rb.add(
					new LinkOutSuggestion()
							.setDestinationName("서울대공원 캠핑장 사이트")
							.setUrl("http://www.seoul.go.kr/storyw/campingjang/park.do")
			);
		}else if(selectedItem.equals("강동")){
			basicCard
					.setTitle("강동 그린웨이 캠핑장")
					.setFormattedText(array.get(8).getAsJsonObject().get("COT_VALUE_01").getAsString())
					.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/강동배경.PNG"));
			rb.addSuggestions(new String[]{"1박 2일","2박 3일","3박 4일"});
		}

		rb.add(simpleResponse);
		rb.add(basicCard);

		return rb.build();
	}

	@ForIntent("SelectDate")
	public ActionResponse SelectDate(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		period = CommonUtil.makeSafeString(request.getParameter("period"));
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech("머무시는 기간은 " +period+"입니다. \n 날짜를 말씀해주세요.")
				.setDisplayText("머무시는 기간은 " +period+"입니다. \n 날짜를 말씀해주세요.")
		;

		basicCard
				.setTitle("날짜를 선택해주세요")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/cal.PNG"));
		rb.addSuggestions(new String[]{"8월 29일","다음주 화요일","9일"});

		rb.add(simpleResponse);
		rb.add(basicCard);

		return rb.build();
	}

	@ForIntent("selectfacilites")
	public ActionResponse selectfacilites(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		SelectionList selectionlist = new SelectionList();

		selectedDate = CommonUtil.makeSafeString(request.getParameter("date"));
		String date2 = selectedDate.substring(5,10);

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech("<speak>선택하신 날짜는"+"<say-as interpret-as=\"date\" format=\"md\">"+date2+"</say-as>"+"입니다.\n 선택하고자 하는 시설을 말씀해주세요.\n</speak>")
				.setDisplayText("선택하신 날짜는 "+date2+"입니다. \n 시설을 말씀해주세요.\n")
		;

		if(selectedItem.equals("난지")){
			selectionlist
					.setTitle("난지 캠핑장")
					.setItems(
							Arrays.asList(
									new ListSelectListItem()
											.setTitle("4인용 가족텐트")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/난지_4인용.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("4인용")),
									new ListSelectListItem()
											.setTitle("6인용 가족텐트")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/난지_6인용.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("6인용")),
									new ListSelectListItem()
											.setTitle("몽골텐트(중)")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/난지_몽골텐트.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("몽골텐트")),
									new ListSelectListItem()
											.setTitle("카라반")
											.setImage(
													new Image()
															.setUrl("http://blogfiles.naver.net/MjAxNzA5MThfMTk0/MDAxNTA1NzEyMTgzNTg4.3wQyrVjbgY_cLSxSNbqL_1G3fzl1A2jALA5R4kdCi1Ig.4IEee4eIMYd55zFnDMez7OD2EE62jJ7nAYIx049kqVYg.JPEG.tpsxj8585/IMG_8687.JPG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("카라반")
											)

							)

					);
		}else if(selectedItem.equals("노을")){
			selectionlist
					.setTitle("노을 캠핑장")
					.setItems(
							Arrays.asList(
									new ListSelectListItem()
											.setTitle("A구역")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/노을시설위치.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("A구역")),
									new ListSelectListItem()
											.setTitle("B구역")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/노을시설위치.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("B구역")),
									new ListSelectListItem()
											.setTitle("CD구역")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/노을시설위치.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("CD구역"))

							)

					);
		}else if(selectedItem.equals("서울대공원")){
			selectionlist
					.setTitle("서울대공원 캠핑장")
					.setItems(
							Arrays.asList(
									new ListSelectListItem()
											.setTitle("1야영장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_1야영장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("1야영장")),
									new ListSelectListItem()
											.setTitle("2야영장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_2야영장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("2야영장")),
									new ListSelectListItem()
											.setTitle("3야영장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_3야영장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("3야영장")),
									new ListSelectListItem()
											.setTitle("4야영장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_4야영장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("4야영장"))
							)

					);
		}else if(selectedItem.equals("강동")) {
			selectionlist
					.setTitle("강동 그린웨이 캠핑장")
					.setItems(
							Arrays.asList(
									new ListSelectListItem()
											.setTitle("오토캠핑장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/강동_오토.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("오토")),
									new ListSelectListItem()
											.setTitle("이팝캠핑장")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/강동_가족캠핑장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("이팝")),
									new ListSelectListItem()
											.setTitle("가족캠핑장(마로니에,청단풍나무,자작나무)")
											.setImage(
													new Image()
															.setUrl("https://actions.o2o.kr/devsvr4/image/강동_가족캠핑장.PNG")
											)
											.setOptionInfo(
													new OptionInfo()
															.setKey("가족"))
							)

					);
		}

		rb.add(simpleResponse);
		rb.add(basicCard);
		rb.add(selectionlist);

		return rb.build();
	}

	@ForIntent("selectseat")
	public ActionResponse selectseat(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		SelectionList selectionlist = new SelectionList();
		selectedfacilites = request.getSelectedOption();

		String date2 = selectedDate.substring(0,10);

		Map<String, Object> data = rb.getConversationData();

		data.clear();

		simpleResponse.setTextToSpeech(selectedfacilites+"시설을 선택하셨습니다.\n 원하시는 자리를 말씀해주세요. 현재 예약가능한 좌석은 다음과 같습니다.")
				.setDisplayText(selectedfacilites+"시설을 선택하셨습니다. \n 자리를 말씀해주세요.\n 현재 예약가능한 좌석은 다음과 같습니다.")
		;
		basicCard
				.setTitle("남아 있는 자리현황")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_좌석.PNG"));

		rb.add(simpleResponse);
		rb.add(basicCard);
		return rb.build();
	}

	@ForIntent("finalcheck")
	public ActionResponse finalcheck(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		String date3 = selectedDate.substring(5,10);

		String urlStr = "http://openapi.seoul.go.kr:8088/6767506b536a773936337a4f656247/json/Mgiscampinginfoseoul/1/9/";
		apiController api = new apiController();
		String st = api.get(urlStr);

		JsonParser jason = new JsonParser();
		JsonObject obj = (JsonObject) jason.parse(st);

		JsonObject objs =  obj.get("Mgiscampinginfoseoul").getAsJsonObject();
		JsonArray array= objs.get("row").getAsJsonArray();

		//String a = array.get(4).getAsJsonObject().get("COT_VALUE_02").getAsString().substring(0,86);

		SimpleResponse simpleResponse = new SimpleResponse();
		SimpleResponse wrongseat = new SimpleResponse();
		BasicCard basicCard = new BasicCard();
		BasicCard basicCard2 = new BasicCard();

		seat = CommonUtil.makeSafeString(request.getParameter("seat"));

		int aa[] = new int[1000];
		for(int i = 101; i < 150; i++){
			aa[i] = 0;
		}
		aa[101] = 1;
		aa[102] = 1;
		aa[104]= 1;
		aa[108] = 1;
		aa[112] = 1;
		aa[121] = 1;

		int seletedItem = Integer.parseInt(seat);
		if((seletedItem == 101)||(seletedItem == 102)||(seletedItem == 104)||(seletedItem == 108)||(seletedItem == 112)){
			for(int i = 101 ; i < 200; i++){
				if((seletedItem == i) && (aa[i] == 1)){
					wrongseat.setTextToSpeech("잘못된 자리입력입니다. 남아있는 자리는 다음과 같습니다. 다시 한번 말씀해주세요.")
							.setDisplayText("잘못된 입력입니다.\n 남아있는 자리는 다음과 같습니다.\n 다시 한번 말씀해주세요");

					basicCard2
							.setTitle("남아 있는 자리현황")
							.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/서울대공원_좌석.PNG"));
					;
				}
			}
			rb.add(wrongseat);
			rb.add(basicCard2);

		}else{
			simpleResponse.setTextToSpeech("<speak>예약확인해드리겠습니다.\n"+selectedItem+"캠핑장,<break time=\"200ms\"/>"+selectedfacilites+"시설로 <break time=\"200ms\"/> "+seat+"자리로 <break time=\"200ms\"/>"+"<say-as interpret-as=\"date\" format=\"md\">"+date3+"</say-as>"+"부터<break time=\"200ms\"/>"+period+"로 예약완료되셨습니다.\n"+"잘못된 예약일 경우 아래 번호로 연락주세요\n" +
					"</speak>")
					.setDisplayText("예약확인해드리겠습니다.\n"+selectedItem+"캠핑장,"+selectedfacilites+" "+seat+"자리로 "+date3+" "+period+" "+" 로 예약완료되셨습니다.\n"+"잘못된 예약일 경우 아래 번호로 연락주세요")
			;

			if(selectedItem.equals("난지")) {
				basicCard
						.setFormattedText(array.get(4).getAsJsonObject().get("COT_VALUE_02").getAsString())
						.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/난지_배경.PNG"));

				rb.addSuggestions(new String[]{"02-304-0061"});
				rb.add(
						new LinkOutSuggestion()
								.setDestinationName("난지 캠핑장 사이트")
								.setUrl("http://www.nanjicamp.com/")
				);
			}else if(selectedItem.equals("노을")){
				basicCard
						.setFormattedText(array.get(5).getAsJsonObject().get("COT_VALUE_02").getAsString().substring(27,219))
						.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/난지_배경.PNG"));

				rb.addSuggestions(new String[]{"02-304-3213"});
				rb.add(
						new LinkOutSuggestion()
								.setDestinationName("노을 캠핑장 사이트")
								.setUrl("http://www.seoul.go.kr/storyw/campingjang/noel.do")
				);
			}else if(selectedItem.equals("서울대공원")){
				basicCard
						.setFormattedText(array.get(6).getAsJsonObject().get("COT_VALUE_02").getAsString())
						.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/난지_배경.PNG"));

				rb.addSuggestions(new String[]{"02-502-3836"});
				rb.add(
						new LinkOutSuggestion()
								.setDestinationName("서울대공원 캠핑장 사이트")
								.setUrl("http://www.seoul.go.kr/storyw/campingjang/park.do")
				);
			}else if(selectedItem.equals("강동")){
				basicCard
						.setFormattedText(array.get(8).getAsJsonObject().get("COT_VALUE_02").getAsString())
						.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/image/난지_배경.PNG"));

				rb.addSuggestions(new String[]{"02-478-4079"});
				rb.add(
						new LinkOutSuggestion()
								.setDestinationName("강동 그린웨이 캠핑장 사이트")
								.setUrl("https://www.seoul.go.kr/story/campingjang/m_index.html")
				);
			}
			rb.add(simpleResponse);
		}
		rb.add(basicCard);
		return rb.build();
	}
}
