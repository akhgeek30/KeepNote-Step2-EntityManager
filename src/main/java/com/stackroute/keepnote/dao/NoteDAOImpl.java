package com.stackroute.keepnote.dao;

import java.util.List;

import com.stackroute.keepnote.model.Note;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database
 * 					transaction. The database transaction happens inside the scope of a persistence
 * 					context.
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	//@Autowired
	//private entityManager entityManager;
	@PersistenceContext
	private EntityManager entityManager;
	/*
	 * Autowiring should be implemented for the entityManager.
	 */

	public NoteDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note)
	{
		entityManager.persist(note);
		entityManager.flush();
		return true;
	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId)
	{
		entityManager.remove(getNoteById(noteId));
		entityManager.flush();
		return true;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes()
	{
		return entityManager.createQuery("FROM Note ORDER BY DATE DESC").getResultList();
	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId)
	{
		List<Note> answer = entityManager.createQuery("FROM Note WHERE Id = " + noteId).getResultList();
		return answer.get(0);

	}

	/* Update existing note */

	public boolean UpdateNote(Note note)
	{
	    entityManager.merge(note);
		return true;
	}

}