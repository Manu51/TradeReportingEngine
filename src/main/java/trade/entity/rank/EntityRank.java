package trade.entity.rank;

import java.time.LocalDate;

public class EntityRank {

	private final int rank;
	private final String entity;
	private final LocalDate date;

	public EntityRank(int rank, String entity, LocalDate date) {
		this.rank = rank;
		this.entity = entity;
		this.date = date;
	}

	public int getRank() {
		return rank;
	}

	public String getEntity() {
		return entity;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public boolean equals(Object obj) {
		final EntityRank other = (EntityRank) obj;

		return other.getRank() == this.getRank() && other.getEntity().equals(this.getEntity())
				&& other.getDate().equals(this.getDate());
	}

	@Override
	public String toString() {
		return "(" + getRank() + ") - " + getEntity();
	}
}
