---
sessionId: session-260611-093735-ahkx
---

# Requirements

### Overview & Goals
Stabilize Voxera backend startup and basic persistence flow so the app can run without MySQL failures and provide working foundational CRUD endpoints for auth/channels/messages.

### Scope
#### In Scope
- Make datasource configuration robust for local development (H2 default, MySQL via environment override).
- Harden core entities with essential DB constraints for `User` and `Message`.
- Complete currently thin/empty service-controller flow for first feature slices:
  - user registration (`/api/auth/register`)
  - channel listing/creation (`/api/channels`)
- Add repository query methods required by these flows.
- Resolve compile blockers caused by model API mismatch.

#### Out of Scope
- Full WhatsApp/Telegram/Discord feature parity.
- Security hardening (JWT, password hashing, authorization roles).
- Real-time messaging (WebSocket) and advanced chat UX.

### Functional Requirements
- App must boot with in-memory DB by default, even when MySQL is unavailable.
- App must allow switching to MySQL via env vars (`VOXERA_DB_URL`, `VOXERA_DB_DRIVER`, `VOXERA_DB_USER`, `VOXERA_DB_PASSWORD`).
- Registration endpoint rejects duplicate usernames.
- Channels endpoint supports optional filtering by category ID.
- Message entity should auto-fill `timestamp` when absent.
- Codebase must compile cleanly after entity/repository/service/controller alignment.

# Technical Design

### Current Implementation
- Entry point: `src/main/java/voxera/VoxeraApplication.java` uses standard `@SpringBootApplication` startup.
- Architecture pattern already present: layered `controller -> service -> repisotory -> entity` under `voxera.*`.
- Relevant entity files:
  - `src/main/java/voxera/entity/User.java`
  - `src/main/java/voxera/entity/Message.java`
  - plus relationship entities (`Channel`, `Server`, `ServerMember`, `ChannelCategory`).
- Relevant repositories:
  - `src/main/java/voxera/repisotory/UserRepository.java`
  - `ChannelRepository.java`, `MessageRepository.java`, `ServerRepository.java`
- Relevant services/controllers were previously mostly empty and now partially filled:
  - `service/userService.java`, `service/channelService.java`, `service/MessageService.java`
  - `controller/authController.java`, `controller/channelController.java`, `controller/MessageController.java`, `controller/ServerController.java`
- Config file: `src/main/resources/application.properties` now supports env-based datasource switching.
- Reported build issue: compile failed earlier due to missing getter/setter methods expected by service/controller logic.

### Key Decisions
- Keep current package and naming convention (`voxera.repisotory`, lowercase service/controller class names) to avoid broad refactor now.
- Use Spring Data derived queries for first filtering needs (`findByCategoryId`, `findByChannelChannelId`) instead of custom JPQL.
- Keep timestamp as `long` in `Message` for compatibility with existing model, with `@PrePersist` fallback.
- Prioritize startup reliability and compile consistency before expanding domain complexity.

### Proposed Changes
- Configuration reliability
  - Keep `application.properties` defaulting to H2 using env placeholders.
  - Preserve MySQL example config as comments for local ops guidance.
- Entity hardening
  - `User`: enforce `nullable`, `unique`, and `length` constraints for username/email; preserve generated ID.
  - `Message`: enforce non-null `content`/`timestamp`; auto-generate timestamp in `@PrePersist`.
- Repository completion
  - `ChannelRepository`: add `List<Channel> findByCategoryId(Integer categoryId)`.
  - `MessageRepository`: add `List<Message> findByChannelChannelId(Integer channelId)` for channel-scoped retrieval.
- Service/controller completion
  - `userService`: add `findAll`, `findByUsername`, `save`.
  - `channelService`: add `findAll`, `findByCategoryId`, `save`.
  - `authController`: implement `POST /api/auth/register` with duplicate username guard.
  - `channelController`: implement `GET /api/channels` (+ optional `categoryId`) and `POST /api/channels`.
- Compile consistency pass
  - Ensure Lombok-generated API used by callers is available and consistent (`@Data` on entities where mutators are needed).
  - Re-run compile and fix any remaining method-signature drift between entities and consumers.

### File Structure
- `src/main/resources/application.properties` (modify)
- `src/main/java/voxera/entity/User.java` (modify)
- `src/main/java/voxera/entity/Message.java` (modify)
- `src/main/java/voxera/repisotory/ChannelRepository.java` (modify)
- `src/main/java/voxera/repisotory/MessageRepository.java` (modify)
- `src/main/java/voxera/service/userService.java` (modify)
- `src/main/java/voxera/service/channelService.java` (modify)
- `src/main/java/voxera/controller/authController.java` (modify)
- `src/main/java/voxera/controller/channelController.java` (modify)

### Risks
- Lowercase class names (`userService`, `authController`) are unconventional and may increase maintenance confusion; keep for now to avoid unnecessary churn.
- Directly exposing entities in controllers can cause future API coupling; acceptable temporarily for bootstrap phase.
- No password hashing yet; must be addressed in a later security-focused iteration.

# Testing

### Validation Approach
- Use project compile as baseline validation for model/service/controller compatibility.
- Verify endpoint behavior with simple HTTP checks (or integration tests if added later).

### Key Scenarios
- App boots with no MySQL env vars and creates H2 datasource.
- Registration with a new username returns success and persists user.
- Registration with duplicate username returns `400 Bad Request`.
- `GET /api/channels` returns all channels.
- `GET /api/channels?categoryId=<id>` returns category-scoped channels.
- Creating a message without explicit timestamp stores an auto-generated timestamp.

### Edge Cases
- Null/blank required fields in entities are rejected by persistence constraints.
- Wrong MySQL env values fail fast but can be bypassed by returning to default H2 config for local run.

# Delivery Steps

### ✓ Step 1: Stabilize datasource configuration and entity constraints
Voxera starts reliably in local development with H2 default and has stricter core persistence constraints.

- Finalize `application.properties` datasource placeholders so H2 is default and MySQL is enabled via `VOXERA_DB_*` env variables.
- Keep clear inline MySQL examples for PowerShell to reduce startup misconfiguration.
- Update `User` entity constraints (`username`, `email`, `password`, description length) in `src/main/java/voxera/entity/User.java`.
- Update `Message` entity constraints and `@PrePersist` timestamp fallback in `src/main/java/voxera/entity/Message.java`.

### ✓ Step 2: Complete repository and service layer for initial user/channel flows
Repository and service APIs provide the queries and operations needed by auth and channel endpoints.

- Extend `ChannelRepository` with category-based lookup and `MessageRepository` with channel-based lookup.
- Implement concrete methods in `userService` (`findAll`, `findByUsername`, `save`) using `UserRepository`.
- Implement concrete methods in `channelService` (`findAll`, `findByCategoryId`, `save`) using `ChannelRepository`.
- Ensure method signatures align with entity fields and repository naming conventions used by Spring Data.

### ✓ Step 3: Wire REST controllers and close compile gaps
Auth and channel controllers expose working endpoints, and project compiles without entity accessor errors.

- Implement `authController` registration endpoint with duplicate-username guard and persistence through `userService`.
- Implement `channelController` list/create endpoints with optional `categoryId` filter.
- Verify current `MessageService`/`MessageController` interactions remain compatible with `Message` model accessors.
- Run compile validation and resolve any remaining missing getter/setter or signature mismatch issues across entity-service-controller calls.