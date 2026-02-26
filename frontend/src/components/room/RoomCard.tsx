import React from 'react';
import styles from './RoomCard.module.css';

interface RoomCardProps {
  type: string;
  description: string;
  date: string;
  price: string;
  image: string;
}

const RoomCard: React.FC<RoomCardProps> = ({ 
    type, 
    description, 
    date, 
    price, 
    image 
}) => {
  return (
    <article className={styles.roomCard}>
      <img src={image} alt={type} className={styles.roomImage} />
      <div className={styles.roomInfo}>
        <h3 className={styles.roomType}>{type}</h3>
        <p className={styles.roomDescription}>{description}</p>
        <span className={styles.roomDate}>{date}</span>
        <span className={styles.roomPrice}>{price}</span>
      </div>
    </article>
  );
};

export default RoomCard;