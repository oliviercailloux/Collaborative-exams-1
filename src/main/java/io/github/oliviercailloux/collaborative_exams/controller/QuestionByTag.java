package io.github.oliviercailloux.collaborative_exams.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import io.github.oliviercailloux.collaborative_exams.Service.PersonService;
import io.github.oliviercailloux.collaborative_exams.Service.PersonTagService;
import io.github.oliviercailloux.collaborative_exams.model.entity.Person;
import io.github.oliviercailloux.collaborative_exams.model.entity.question.Question;

@Path("QuestionByTag")
public class QuestionByTag {
	
	@Inject
	private PersonService personService;
	
	@Inject
	private PersonTagService personTagService;
	
	@Path("ByTag")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Question> getQuestions(@QueryParam("idAuthor") Integer idAuthor, @QueryParam("tag") String tag)throws Exception {
		
		Person person = personService.findPerson(idAuthor);
		
		List<Question> questions = person.getQuestions();
		List<Question> questionsByTag = new ArrayList<>();
		
		for (Question q : questions) {
			if (personTagService.findPersonTag(q,person) == null)
				throw new WebApplicationException("No tag for this question");
			else {
				if((personTagService.findPersonTag(q,person).getTag()).equals(tag)) {
					questionsByTag.add(q);
				}
			}
		}
		return questionsByTag;
	}
}
