import React from 'react';
import { Link } from 'react-router-dom';
import styles from './RoomCard.module.css';

interface RoomCardProps {
  id: number; 
  type: string;
  description: string;
  date: string;
  price: string;
  image: string;
}

const RoomCard: React.FC<RoomCardProps> = ({ id, type, description, date, price, image }) => {
  return (
    <Link to={`/quarto/${id}`} className={styles.cardLink}>
      <article className={styles.roomCard}>
        <img src={image} alt={type} className={styles.roomImage} />
        <div className={styles.roomInfo}>
          <h3 className={styles.roomType}>{type}</h3>
          <p className={styles.roomDescription}>{description}</p>
          <span className={styles.roomDate}>{date}</span>
          <span className={styles.roomPrice}>{price}</span>
        </div>
      </article>
    </Link>
  );
};

export default RoomCard;