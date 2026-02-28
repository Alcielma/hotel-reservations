import { forwardRef } from "react";
import styles from "./Date.module.css";

interface DateProps {
  onChange: (event: { target: { name: string; value: string } }) => void;
  name: string;
  message: string;
  label: string;
  required: boolean;
  value: string;
  disabled: boolean;
  min?: string; 
}

const DateInput = forwardRef<HTMLInputElement, DateProps>(({
  onChange, name, label, required, value, disabled, min
}, ref) => {
  return (
    <div className={styles.container}>
      <label htmlFor={name} className={styles.label}>
        {label} {required && <span className={styles.required}>*</span>}
      </label>
      <input
        type="date"
        id={name}
        name={name}
        className={styles.input}
        value={value}
        min={min} 
        onChange={(e) => onChange({ target: { name: e.target.name, value: e.target.value } })}
        disabled={disabled}
        required={required}
        ref={ref}
      />
    </div>
  );
});

DateInput.displayName = "DateInput"; 
export default DateInput;