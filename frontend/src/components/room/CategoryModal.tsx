import React from 'react';
import styles from '../../pages/NewRoomPage.module.css'; 
interface CategoryModalProps {
  onClose: () => void;
  onSave: (categoria: { nome: string; precoDiaria: number; capacidade: number }) => void;
}

const CategoryModal: React.FC<CategoryModalProps> = ({ onClose, onSave }) => {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    
    onSave({
      nome: formData.get('nome') as string,
      precoDiaria: Number(formData.get('precoDiaria')),
      capacidade: Number(formData.get('capacidade')),
    });
  };

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.miniDialog} style={{ width: '400px' }} onClick={e => e.stopPropagation()}>
        <h3 style={{ marginBottom: '1.5rem', color: '#1e293b' }}>Nova Categoria</h3>
        
        <form onSubmit={handleSubmit} className={styles.formSection}>
          <div className={styles.inputGroup}>
            <label>Nome da Categoria</label>
            <input name="nome" type="text" placeholder="Ex: Suíte Master" required />
          </div>

          <div className={styles.row}>
            <div className={styles.inputGroup}>
              <label>Diária (R$)</label>
              <input name="precoDiaria" type="number" step="0.01" placeholder="0.00" required />
            </div>
            <div className={styles.inputGroup}>
              <label>Capacidade</label>
              <input name="capacidade" type="number" placeholder="Ex: 2" required />
            </div>
          </div>

          <div className={styles.dialogBtns} style={{ marginTop: '1rem' }}>
            <button type="button" onClick={onClose}>Cancelar</button>
            <button type="submit" className={styles.confirmBtn}>Criar Categoria</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CategoryModal;